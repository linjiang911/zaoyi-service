package com.zaoyi.service.web.filer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zaoyi.service.common.util.ExtraSpringHibernateTemplate;
import com.zaoyi.service.common.util.Sessions;
import com.zaoyi.service.common.util.SpringContextUtils;
import com.zaoyi.service.pub.bsc.dao.po.AdminUser;
import com.zaoyi.service.pub.bsc.dao.po.Menu;

public class ViewPermissionChecker {
	private static final String SUPER_ADMIN_USER_ACCOUNT = "admin";
	private static final String PAGE_LOGIN = "/login.jsp";
	private static final String PAGE_PERMISSION_DENIED = "/permission_denied.jsp";
	private static final List<String> PUBLIC_PAGES = new ArrayList<>();
	private static final List<String> AUTHC_PAGES = new ArrayList<>();

	static {
		PUBLIC_PAGES.add(PAGE_LOGIN);
		AUTHC_PAGES.add(PAGE_PERMISSION_DENIED);
		AUTHC_PAGES.add("/upload_result.jsp");
		AUTHC_PAGES.add("/jump.jsp");
	}

	public static boolean canAccessPage(String page, HttpServletRequest httpReq) throws PermissionDeniedException, NotLoginedException {
		Object loginedUserObject = Sessions.getLoginedUser(httpReq.getSession());
		AdminUser adminUser = loginedUserObject instanceof AdminUser ? (AdminUser) loginedUserObject : null;

		if (PUBLIC_PAGES.contains(page)) {
			// 特殊页面，公共权限，无需登录
			return true;
		}

		if (adminUser == null) {
			// 未登录
			throw new NotLoginedException();
		}

		if (AUTHC_PAGES.contains(page)) {
			// 特殊页面，任何用户登录后即可访问
			return true;
		}

		if (isSuperAdminUser(adminUser)) {
			// 超级管理员，拥有最高权限
			return true;
		}

		if (existPermission(adminUser, page)) {
			// 满足权限
			return true;
		} else {
			// 不满足权限
			throw new PermissionDeniedException();
		}
	}

	public static void sendRedirectPageLogin(HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException {
		httpResp.sendRedirect(httpReq.getContextPath() + PAGE_LOGIN);
	}

	public static void sendRedirectPagePermissionDenied(HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException {
		httpResp.sendRedirect(httpReq.getContextPath() + PAGE_PERMISSION_DENIED);
	}
	
	@SuppressWarnings("unchecked")
	private static boolean existPermission(AdminUser loginedUser, String page) {
		ExtraSpringHibernateTemplate extraSpringHibernateTemplate = SpringContextUtils.getApplicationContext().getBean(ExtraSpringHibernateTemplate.class);
		Integer roleId = loginedUser.getRoleId();
		String hql = "select m FROM Permission p, RolePermission rp, PermissionMenu pm ,Menu m WHERE p.provinceId=rp.provinceId AND pm.provinceId =p.provinceId AND m.menuId=pm.MenuId AND rp.roleId=?";
		List<Menu> menus = (List<Menu>) extraSpringHibernateTemplate.getHibernateTemplate().find(hql, roleId);
		for (Menu menu : menus) {
			if (menu.getUrl().equals(page)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isSuperAdminUser(AdminUser loginedUser) {
		return SUPER_ADMIN_USER_ACCOUNT.equals(loginedUser.getAccount());
	}
}

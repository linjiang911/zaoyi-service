package com.zaoyi.service.web.filer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zaoyi.service.common.util.Sessions;
import com.zaoyi.service.pub.bsc.dao.po.AdminUser;

public class AdminViewPermissionChecker {
	private static final String SUPER_ADMIN_USER_ACCOUNT = "admin";
	private static final String PAGE_LOGIN = "/admin/login.jsp";
	private static final String PAGE_404 = "/admin/login.jsp";
	private static final String page_500 = "/admin/login.jsp";
	private static final String PAGE_PERMISSION_DENIED = "/platform_permission_denied.jsp";
	private static final List<String> PUBLIC_PAGES = new ArrayList<>();
	private static final List<String> AUTHC_PAGES = new ArrayList<>();

	static {
		PUBLIC_PAGES.add(PAGE_LOGIN);
		AUTHC_PAGES.add(PAGE_PERMISSION_DENIED);
		AUTHC_PAGES.add(PAGE_404);
		AUTHC_PAGES.add(page_500);
	}

	public static boolean canAccessPage(String page, HttpServletRequest httpReq) throws NotLoginedException {
		Object loginedUserObject = Sessions.getLoginedUser(httpReq.getSession());
		AdminUser platformAdminUser = loginedUserObject instanceof AdminUser ? (AdminUser) loginedUserObject : null;

		if (PUBLIC_PAGES.contains(page)) {
			// 公共权限，无需登录
			return true;
		}

		if (platformAdminUser == null) {
			// 未登录
			throw new NotLoginedException();
		}

		if (AUTHC_PAGES.contains(page)) {
			// 特殊页面，任何用户登录后即可访问
			return true;
		}

		if (isSuperAdminUser(platformAdminUser)) {
			// 超级管理员，拥有最高权限
			return true;
		}

		// TODO 加入权限验证
		return true;
	}

	public static void sendRedirectPageLogin(HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException {
		httpResp.sendRedirect(httpReq.getContextPath() + PAGE_LOGIN);
	}

	public static void sendRedirectPagePermissionDenied(HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException {
		httpResp.sendRedirect(httpReq.getContextPath() + PAGE_PERMISSION_DENIED);
	}

	private static boolean isSuperAdminUser(AdminUser adminUser) {
		return SUPER_ADMIN_USER_ACCOUNT.equals(adminUser.getAccount());
	}
}

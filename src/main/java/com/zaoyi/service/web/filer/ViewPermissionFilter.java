package com.zaoyi.service.web.filer;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewPermissionFilter implements Filter {
	private static final String PLATFORM_PAGE_PREFIX = "/admin";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpResp = (HttpServletResponse) resp;
		String page = getPage(httpReq);
		if (page.startsWith(PLATFORM_PAGE_PREFIX)) {
			// 联盟页面
			try {
				if (AdminViewPermissionChecker.canAccessPage(page, httpReq)) {
					filterChain.doFilter(req, resp);
				}
			} catch (NotLoginedException e) {
				AdminViewPermissionChecker.sendRedirectPageLogin(httpReq, httpResp);
			}
		} else {
			// 孵化器页面
			try {
				if (ViewPermissionChecker.canAccessPage(page, httpReq)) {
					filterChain.doFilter(req, resp);
				}
			} catch (NotLoginedException e) {
				ViewPermissionChecker.sendRedirectPageLogin(httpReq, httpResp);
			} catch (PermissionDeniedException e) {
				ViewPermissionChecker.sendRedirectPagePermissionDenied(httpReq, httpResp);
			}
		}
	}

	private static String getPage(HttpServletRequest httpReq) {
		return httpReq.getRequestURI().replaceFirst("^" + httpReq.getContextPath(), "");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}
}

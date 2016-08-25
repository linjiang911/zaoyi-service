package com.zaoyi.service.common.util;

import javax.servlet.http.HttpSession;

public class Sessions {
	public static String attr_name_logined_user = "loginedUser";
	public static String attr_name_logined_opinId = "loginedOpinId";

	public static void setLoginedUser(Object user, HttpSession session) {
		session.setAttribute(attr_name_logined_user, user);
	}

	public static void removeLoginedUser(HttpSession session) {
		session.removeAttribute(attr_name_logined_user);
	}

	public static Object getLoginedUser(HttpSession session) {
		return session.getAttribute(attr_name_logined_user);
	}

	public static boolean isLogined(HttpSession session) {
		return getLoginedUser(session) != null;
	}
	
	
	public static void setLoginedOpinId(Object opinId, HttpSession session) {
		session.setAttribute(attr_name_logined_opinId, opinId);
	}

	public static void removeLoginedOpinId(HttpSession session) {
		session.removeAttribute(attr_name_logined_opinId);
	}

	public static Object getLoginedOpinId(HttpSession session) {
		return session.getAttribute(attr_name_logined_opinId);
	}

	public static boolean isLoginedOpinId(HttpSession session) {
		return getLoginedOpinId(session) != null;
	}
}

package com.zaoyi.service.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtils implements ApplicationContextAware {
	private static ApplicationContext app_context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		
	}

/*	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		app_context = appContext;
		_afterSetApplicationContext();
	}

	public static ApplicationContext getApplicationContext() {
		return app_context;
	}

	private void _afterSetApplicationContext() {
		WeixinUtils.init(app_context.getBean(PlatformSystemConfigService.class).query());
	}*/
}

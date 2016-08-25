package com.zaoyi.service.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.jdbc.support.JdbcUtils;

public class SMSConfig {

	public final static String URL = getConfig("URL");
	
	public final static String ACCOUNT = getConfig("ACCOUNT");
	
	public final static String PASSWORD = getConfig("PASSWORD");
	
	
	private static String getConfig(String key){
		InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("smsConfig.properties");
    	Properties prop = new Properties();
    	try {
			prop.load(inputStream);
			return prop.getProperty(key);
    	}catch (IOException e) {
			// TODO: handle exception
    		return null;
		}
	}
}

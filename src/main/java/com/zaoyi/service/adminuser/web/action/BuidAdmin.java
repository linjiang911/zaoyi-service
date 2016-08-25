package com.zaoyi.service.adminuser.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zaoyi.service.common.util.ExtraSpringHibernateTemplate;
import com.zaoyi.service.pub.bsc.dao.po.AdminUser;

public class BuidAdmin {
	private final static Logger logger = LoggerFactory.getLogger(BuidAdmin.class);
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.cfg.xml");
		ExtraSpringHibernateTemplate extraSpringHibernateTemplate = context.getBean(ExtraSpringHibernateTemplate.class);
		AdminUser entityAU = extraSpringHibernateTemplate.findFirstOneByHQL("From AdminUser where account=?", "admin");
		if(entityAU==null){
			AdminUser adminUser = new AdminUser();
			adminUser.setAccount("admin");
			adminUser.setPassword("123");
			adminUser.setDtCreat(System.currentTimeMillis());
			adminUser.setType(AdminUser.TYPE_ADMIN_SUPER);
			adminUser.setName("林江");
			adminUser.setRealname("超级管理员");
			adminUser.setDepartment("ceo");
			extraSpringHibernateTemplate.saveByPoc(adminUser);
		}
	}
}

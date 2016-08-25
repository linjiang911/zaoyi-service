package com.zaoyi.service.adminuser.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaoyi.service.adminuser.AdminUserServiec;
import com.zaoyi.service.common.util.ExtraSpringHibernateTemplate;
import com.zaoyi.service.common.util.Paging;
import com.zaoyi.service.pub.bsc.dao.po.AdminUser;


@Service
public class AdminUserServiecImpl implements AdminUserServiec {
	private static Class<?> poc=AdminUser.class;
	@Autowired
	private ExtraSpringHibernateTemplate extraSpringHibernateTemplate;
	@Override
	public Paging<AdminUser> queryPageByName(int firstResult,int maxResults,Object...object) {
		String hql="From AdminUser where name like ?";
		String object1="%s%";
		return extraSpringHibernateTemplate.findPageByHQL(hql, firstResult, maxResults, object1);
	}

	@Override
	public void sava(AdminUser poc) {
		extraSpringHibernateTemplate.saveByPoc(poc);
	}

	@Override
	public void saveAll(List<AdminUser> listAdminUser) {
		extraSpringHibernateTemplate.saveAll(listAdminUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdminUser> findALL() {
		return (List<AdminUser>) extraSpringHibernateTemplate.findAll(poc);
		
	}

	@Override
	public List<AdminUser> findALLByHQL(String hql, Object... object) {
		return extraSpringHibernateTemplate.findAllByHQL(hql, object);
	}

	@Override
	public AdminUser findByFristOneHQL(String hql, Object...object) throws Exception {
		return extraSpringHibernateTemplate.findFirstOneByHQL(hql, object);
	}

	@Override
	public AdminUser findOneFrist(String propName, Object object) {
		return extraSpringHibernateTemplate.findFirstOneByPropEq(poc, propName, object);
	}
	
}

package com.zaoyi.service.common.util;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;

public interface ExtraSpringHibernateTemplate {

	long count(Class<?> poc) throws DataAccessException;

	<T> List<T> findAll(Class<T> poc) throws DataAccessException;

	// -- 
	<T> Paging<T> findPageByHQL(String hql,int firstResult, int maxResults,Object...object)
			throws DataAccessException;

	<T> Paging<T> findPage(Class<?> poc, Order order, int firstResult, int maxResults) throws DataAccessException;

	<T> Paging<T> findPageByCriteria(DetachedCriteria queryListCriteria, final DetachedCriteria queryCountCriteria,
			int firstResult, int maxResults) throws DataAccessException;

	// -- FirstOne
	<T> T findFirstOneByCriteria(DetachedCriteria criteria) throws DataAccessException;

	<T> T findFirstOneByHQL(String queryString, Object...object)throws Exception;
	
	<T> T findFirstOneByPropEq(Class<?> poc, String propName, Object propValue, Order order) throws DataAccessException;

	<T> T findFirstOneByPropEq(Class<?> poc, String propName, Object propValue) throws DataAccessException;

	void deleteFirstOneByPropEq(Class<?> poc, String propName, Object propValue) throws DataAccessException;

	// -- All
	
	<T> List<T> findAllByHQL(String queryString, Object...object)
			throws DataAccessException;
	<T> List<T> findAllByCriteria(DetachedCriteria criteria) throws DataAccessException;

	<T> List<T> findAllByPropEq(Class<?> poc, String propName, Object propValue, Order order)
			throws DataAccessException;

	<T> List<T> findAllByPropEq(Class<?> poc, String propName, Object propValue) throws DataAccessException;

	void deleteAllByPropEq(Class<?> poc, String propName, Object propValue) throws DataAccessException;
	
	<T> void saveAll(List<T> list) throws DataAccessException;
	
	<T> void updataAll(List<T> list) throws DataAccessException;
	
	<T> void updataByPoc(T poc) throws DataAccessException;
	
	<T> void deleteByPoc(T poc) throws DataAccessException;
	
	<T> void saveByPoc(T poc) throws DataAccessException;
	// --

	HibernateTemplate getHibernateTemplate();

	void setHibernateTemplate(HibernateTemplate hibernateTemplate);

	// -- 

	static class C {
		public static DetachedCriteria build(Class<?> clazz, Order order, Criterion criterion) {
			DetachedCriteria detached = DetachedCriteria.forClass(clazz);
			detached.addOrder(order);
			detached.add(criterion);
			return detached;
		}

		public static DetachedCriteria build(Class<?> clazz, Criterion criterion) {
			DetachedCriteria detached = DetachedCriteria.forClass(clazz);
			detached.add(criterion);
			return detached;
		}

		public static DetachedCriteria buildRowCount(Class<?> clazz, Criterion criterion) {
			DetachedCriteria detached = DetachedCriteria.forClass(clazz);
			detached.setProjection(Projections.rowCount());
			detached.add(criterion);
			return detached;
		}
	}


}

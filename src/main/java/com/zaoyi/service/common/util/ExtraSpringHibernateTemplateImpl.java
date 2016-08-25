package com.zaoyi.service.common.util;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

public class ExtraSpringHibernateTemplateImpl implements ExtraSpringHibernateTemplate {
	
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public long count(final Class<?> poc) throws DataAccessException {
		return hibernateTemplate.executeWithNativeSession(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException {
				Criteria executableCriteria = DetachedCriteria.forClass(poc).setProjection(Projections.rowCount())
						.getExecutableCriteria(session);
				return (Long) executableCriteria.uniqueResult();
			}
		});
	}

	@Override
	public <T> List<T> findAll(Class<T> poc) throws DataAccessException {
		return hibernateTemplate.loadAll(poc);
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public <T> Paging<T> findPage(Class<?> poc, Order order, int firstResult, int maxResults)
			throws DataAccessException {
		List<T> list = (List<T>) hibernateTemplate.findByCriteria(DetachedCriteria.forClass(poc).addOrder(order),
				firstResult, maxResults);
		long count = count(poc);
		return new Paging<T>(list, firstResult, maxResults, (int) count);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Paging<T> findPageByCriteria(DetachedCriteria queryListCriteria,
			final DetachedCriteria queryCountCriteria, int firstResult, int maxResults) throws DataAccessException {
		List<T> list = (List<T>) hibernateTemplate.findByCriteria(queryListCriteria, firstResult, maxResults);
		long count = hibernateTemplate.executeWithNativeSession(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException {
				Criteria executableCriteria = queryCountCriteria.getExecutableCriteria(session);
				return (Long) executableCriteria.uniqueResult();
			}
		});
		return new Paging<T>(list, firstResult, maxResults, (int) count);
	}
	@Override
	@SuppressWarnings("unchecked")
	public <T> Paging<T> findPageByHQL(final String hql, final int firstResult,final int maxResults,final Object...object) throws DataAccessException {
		List<T> list = (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback<Object>() {     
		    public Object doInHibernate(Session session)throws HibernateException {     
		     Query query = session.createQuery(hql);     
		     for(int i=0;i<object.length;i++){
		    	 query.setParameter(i, object[i]);
		     }
		     query.setFirstResult(firstResult);     
		     query.setMaxResults(maxResults);     
		     List<T> list = query.list();     
		     return list;     
		    }     
		   });
		long count = hibernateTemplate.executeWithNativeSession(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException {
				Query query = session.createQuery(hql);     
			     for(int i=0;i<object.length;i++){
			    	 query.setParameter(i, object[i]);
			     }
				return (long) query.list().size();
			}
		});
		return new Paging<T>(list, firstResult, maxResults, (int) count);
	}
	// -- FirstOne

	@Override
	public <T> T findFirstOneByCriteria(final DetachedCriteria criteria) throws DataAccessException {
		return hibernateTemplate.executeWithNativeSession(new HibernateCallback<T>() {
			@Override
			@SuppressWarnings("unchecked")
			public T doInHibernate(Session session) throws HibernateException {
				Criteria executableCriteria = criteria.getExecutableCriteria(session);
				executableCriteria.setMaxResults(1);
				return (T) executableCriteria.uniqueResult();
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T findFirstOneByHQL(String queryString,Object...object) throws Exception {
		List<T> find = (List<T>) hibernateTemplate.find(queryString, object);
		if(find.size()>0){
			return find.get(0);
		}
		return null;
	}
	
	@Override
	public <T> T findFirstOneByPropEq(Class<?> poc, String propName, Object propValue, Order order)
			throws DataAccessException {
		DetachedCriteria detached = DetachedCriteria.forClass(poc);
		detached.add(Restrictions.eq(propName, propValue));
		if (order != null) {
			detached.addOrder(order);
		}
		return findFirstOneByCriteria(detached);
	}

	@Override
	public <T> T findFirstOneByPropEq(Class<?> poc, String propName, Object propValue) throws DataAccessException {
		return findFirstOneByPropEq(poc, propName, propValue, null);
	}

	@Override
	public void deleteFirstOneByPropEq(Class<?> poc, String propName, Object propValue) throws DataAccessException {
		hibernateTemplate.delete(findFirstOneByPropEq(poc, propName, propValue));
	}

	// -- All

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> findAllByCriteria(DetachedCriteria criteria) throws DataAccessException {
		return (List<T>) hibernateTemplate.findByCriteria(criteria);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> findAllByHQL(String queryString,Object...object) throws DataAccessException {
		return (List<T>) hibernateTemplate.find(queryString, object);
	}
	
	@Override
	public <T> List<T> findAllByPropEq(Class<?> poc, String propName, Object propValue, Order order)
			throws DataAccessException {
		DetachedCriteria detached = DetachedCriteria.forClass(poc);
		detached.add(Restrictions.eq(propName, propValue));
		if (order != null) {
			detached.addOrder(order);
		}
		return findAllByCriteria(detached);
	}

	@Override
	public <T> List<T> findAllByPropEq(Class<?> poc, String propName, Object propValue) throws DataAccessException {
		return findAllByPropEq(poc, propName, propValue, null);
	}

	@Override
	public void deleteAllByPropEq(Class<?> poc, String propName, Object propValue) throws DataAccessException {
		hibernateTemplate.deleteAll(findAllByPropEq(poc, propName, propValue));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> void saveAll(final List<T> list) throws DataAccessException {
		hibernateTemplate.executeWithNativeSession(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException {
				for(int i=0;i<list.size();i++){
					session.save(list.get(i));
					if(i%100==0){
						session.flush();
						session.clear();
					}
				}
				return 1L;
			}
		});
	}
	@Override
	public <T> void updataAll(final List<T> list) throws DataAccessException {
		hibernateTemplate.executeWithNativeSession(new HibernateCallback<Long>() {
			@Override
			public Long doInHibernate(Session session) throws HibernateException {
				for(int i=0;i<list.size();i++){
					session.save(list.get(i));
					if(i%100==0){
						session.flush();
						session.clear();
					}
				}
				return 1L;
			}
		});
		
	}
	
	@Override
	public <T> void updataByPoc(T poc) throws DataAccessException {
		hibernateTemplate.update(poc);
	}

	@Override
	public <T> void deleteByPoc(T poc) throws DataAccessException {
		hibernateTemplate.delete(poc);
	}

	@Override
	public <T> void saveByPoc(T poc) throws DataAccessException {
		hibernateTemplate.save(poc);
	}
	
	// 依赖注入

	@Override
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Override
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}

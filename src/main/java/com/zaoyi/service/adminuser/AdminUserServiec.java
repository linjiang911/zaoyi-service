package com.zaoyi.service.adminuser;

import java.util.List;

import com.zaoyi.service.common.util.Paging;
import com.zaoyi.service.pub.bsc.dao.po.AdminUser;
/**
 * adminUser
 * @author linjiang
 *
 */
public interface AdminUserServiec {
	
	Paging<AdminUser> queryPageByName(int firstResult,int maxResults,Object...object);
	
	void sava(AdminUser adminUser);

	void saveAll(List<AdminUser> listAdminUser);
	
	List<AdminUser> findALL();

	List<AdminUser> findALLByHQL(String hql, Object...object);

	AdminUser findByFristOneHQL(String hql, Object...object)throws Exception;

	AdminUser findOneFrist(String propName, Object object);
	
}

package com.zaoyi.service.adminuser.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zaoyi.service.adminuser.AdminUserServiec;
import com.zaoyi.service.common.util.BackendErrorCode;
import com.zaoyi.service.common.util.CommonResp;
import com.zaoyi.service.common.util.Paging;
import com.zaoyi.service.common.util.Sessions;
import com.zaoyi.service.common.util.Strings;
import com.zaoyi.service.pub.bsc.dao.po.AdminUser;

@Controller
@RequestMapping(value="adminUser",produces={"text/json;charset=UTF-8"})
public class AdminUserAction {
	
	private Class<?> poc=AdminUser.class;
	@Autowired
	private AdminUserServiec adminUserServiec;
	
	@ResponseBody
	@RequestMapping(value="login")
	public String login(String account,String pwd,HttpSession session) throws Exception{
		if(StringUtils.isBlank(pwd)||StringUtils.isBlank(account)){
			return CommonResp.buildErrorResp(BackendErrorCode.ACCOUNTORPWD_CANNOT_NULL).toJsonString();
		}
		if(adminUserServiec.findOneFrist("account", account)==null||adminUserServiec.findByFristOneHQL("From AdminUser where account=? and password=?", account,pwd)==null){
			return CommonResp.buildErrorResp(BackendErrorCode.ACCOUNTORPWD_NOT_EXISTORERROR).toJsonString();
		}
		AdminUser adminUser = adminUserServiec.findByFristOneHQL("From AdminUser where account=? and password=?", account,pwd);
		Sessions.setLoginedUser(adminUser, session);
		return CommonResp.buildSuccessResp().toJsonString();
	}
	
	@ResponseBody
	@RequestMapping(value="queryPageByName")
	public String queryPageByName(){
		Paging<AdminUser> pageAdminUser = adminUserServiec.queryPageByName(1,2,"s");
		return CommonResp.buildSuccessResp(pageAdminUser).toJsonString();
	}
	/**
	 * 分页测试
	 * @param name
	 * @param pwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addAdminUser")
	public String addAdminUser(String name ,String pwd){
		AdminUser adminUser = new AdminUser();
		adminUser.setName(name);
		adminUser.setPassword(pwd);
		adminUserServiec.sava(adminUser);
		return CommonResp.buildSuccessResp().toJsonString();
	}
	/**
	 * 批量测试
	 * @param name
	 * @param pwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addAllAdminUser")
	public String addAllAdminUser(String name ,String pwd){
		List<AdminUser> listAdminUser = new ArrayList<AdminUser>();
		for(int i=0;i<10000;i++){
			AdminUser adminUser = new AdminUser();
			adminUser.setName(name);
			adminUser.setPassword(pwd);
			listAdminUser.add(adminUser);
		}
		
		adminUserServiec.saveAll(listAdminUser);
		return CommonResp.buildSuccessResp().toJsonString();
	}
}

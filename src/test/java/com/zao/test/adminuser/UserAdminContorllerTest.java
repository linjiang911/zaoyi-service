package com.zao.test.adminuser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.zao.test.util.SpringJunitController;
import com.zaoyi.service.common.util.CommonResp;

public class UserAdminContorllerTest extends SpringJunitController {
	/**控制层junit实例
	 * 注意几个静态导包
	 * 尤其注意post的链接地址一定要多加/“/user/queryByName”,不能写出“user/queryByName”
	 * @throws Exception
	 */
	@Test
	public void testQueryPageByName() throws Exception {
		String sendHttp = sendHttp("/adminUser/queryPageByName");
		CommonResp buildSuccessResp = CommonResp.buildSuccessResp(sendHttp);
		assertEquals(buildSuccessResp.getCode()==0, true);
	}

	@Test
	public void testAddAdminUser() throws Exception {
		String sendHttp = sendHttp("/adminUser/addAdminUser","name","wom,ss");
		CommonResp buildSuccessResp = CommonResp.buildSuccessResp(sendHttp);
		assertEquals(buildSuccessResp.getCode()==0, true);
	}

	@Test
	public void testAddAllAdminUser() throws Exception {
		String sendHttp = sendHttp("/adminUser/addAllAdminUser","name","wom,ss");
		CommonResp buildSuccessResp = CommonResp.buildSuccessResp(sendHttp);
		assertEquals(buildSuccessResp.getCode()==0, true);
	}
}

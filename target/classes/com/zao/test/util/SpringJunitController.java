package com.zao.test.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:spring.cfg.xml")
public abstract class SpringJunitController {
	
	static RestTemplate restTemplate;
    ObjectMapper objectMapper;//JSON
    String baseUri = "http://localhost:8080/users";
	
	@Autowired
	WebApplicationContext wac;
	protected MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	/**
	 * 发送连接，并回掉url的一个方法，响应改方法的String
	 * 尤其注意post的链接地址一定要多加/“/user/queryByName”,不能写出“user/queryByName”
	 * String 参数成对出现的 key,value 形式，如果value为多值直接,号分开如"ss,dd"
	 * @throws Exception 
	 * 
	 */
	protected String sendHttp(String url,String...str) throws Exception{
		MultiValueMap<String,String> params=new LinkedMultiValueMap<String,String>();
		for(int i=0;i<str.length;i+=2){
			params.add(str[i], str[i+1]);
		}
		MvcResult andReturn = mvc.perform(post(url).params(params))
				.andExpect(status().isOk())
				.andDo(print()).andReturn();
		String contentAsString = andReturn.getResponse().getContentAsString();
		return contentAsString;
	}
}

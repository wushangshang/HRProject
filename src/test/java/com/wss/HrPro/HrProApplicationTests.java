package com.wss.HrPro;

import com.wss.HrPro.entity.po.AccountUser;
import com.wss.HrPro.mapper.AccountUserMapper;
import com.wss.HrPro.service.AccountUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HrProApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private AccountUserMapper accountUserMapper;
	@Autowired
	private AccountUserService accountUserService;

	@Test
	public void testSelect() {
		System.out.println(("----- selectAll method test ------"));

	}



}

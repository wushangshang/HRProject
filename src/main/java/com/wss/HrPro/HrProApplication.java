package com.wss.HrPro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.wss.HrPro.mapper")
@EnableTransactionManagement
public class HrProApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrProApplication.class, args);
	}

}

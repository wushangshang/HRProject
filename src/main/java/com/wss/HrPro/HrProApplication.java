package com.wss.HrPro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wss.HrPro.mapper")
public class HrProApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrProApplication.class, args);
	}

}

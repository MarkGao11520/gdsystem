package com.iss.yzsxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class GdsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdsystemApplication.class, args);
	}
}

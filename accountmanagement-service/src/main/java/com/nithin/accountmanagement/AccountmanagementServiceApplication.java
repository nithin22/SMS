package com.nithin.accountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AccountmanagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountmanagementServiceApplication.class, args);
	}

}

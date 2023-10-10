package com.dh.rest.api.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.dh.rest.api.*")
public class CamelEndpointBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelEndpointBackendApplication.class, args);
	}

}

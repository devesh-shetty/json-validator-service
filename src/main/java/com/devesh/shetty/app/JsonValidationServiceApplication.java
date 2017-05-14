package com.devesh.shetty.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.devesh.shetty")
public class JsonValidationServiceApplication {

	public static void main(String[] args) {
	  SpringApplication.run(JsonValidationServiceApplication.class, args);
	}
}

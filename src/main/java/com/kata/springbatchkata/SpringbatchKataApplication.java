package com.kata.springbatchkata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringbatchKataApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchKataApplication.class, args);
	}

}

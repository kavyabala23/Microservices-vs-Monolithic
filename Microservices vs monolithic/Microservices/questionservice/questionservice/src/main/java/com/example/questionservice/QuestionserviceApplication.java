package com.example.questionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class QuestionserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionserviceApplication.class, args);
	}

}

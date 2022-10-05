package com.naverapi.naverapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NaverapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaverapiApplication.class, args);
	}

}

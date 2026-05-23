package com.poq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PoqApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoqApplication.class, args);
	}

}

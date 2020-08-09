package com.tselree.extractor2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Extractor2Application {

	public static void main(String[] args) {
		SpringApplication.run(Extractor2Application.class, args);
	}

}

package com.edutech.team26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = "com.edutech.team26.domain")
public class Team26Application {

	public static void main(String[] args) {
		SpringApplication.run(Team26Application.class, args);
	}

}

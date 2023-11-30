package com.edutech.team26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Team26Application {

	public static void main(String[] args) {
		SpringApplication.run(Team26Application.class, args);
	}

}

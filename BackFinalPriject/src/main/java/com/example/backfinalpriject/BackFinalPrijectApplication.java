package com.example.backfinalpriject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "customAwareAudit")
@SpringBootApplication
public class BackFinalPrijectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackFinalPrijectApplication.class, args);
	}

}

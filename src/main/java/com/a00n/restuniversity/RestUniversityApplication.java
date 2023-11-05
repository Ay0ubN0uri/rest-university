package com.a00n.restuniversity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "com.a00n.entities" })
@ComponentScan(basePackages = {
		"com.a00n.controllers",
		"com.a00n.services",
		"com.a00n.exceptions"
})
@EnableJpaRepositories(basePackages = { "com.a00n.repositories" })
public class RestUniversityApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestUniversityApplication.class, args);
	}

}

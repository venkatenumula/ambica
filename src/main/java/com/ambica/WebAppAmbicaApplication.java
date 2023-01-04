package com.ambica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.ambica.*")
public class WebAppAmbicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppAmbicaApplication.class, args);
	}

}

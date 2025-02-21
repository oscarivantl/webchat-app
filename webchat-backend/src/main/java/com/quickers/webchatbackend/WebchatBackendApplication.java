package com.quickers.webchatbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WebchatBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebchatBackendApplication.class, args);
	}

}

package com.jo2seo.aomd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AomdApplication {

	public static void main(String[] args) {
		SpringApplication.run(AomdApplication.class, args);
	}

}

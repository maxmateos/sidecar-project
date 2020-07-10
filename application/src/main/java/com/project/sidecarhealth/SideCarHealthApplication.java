package com.project.sidecarhealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.project.sidecarhealth")
@SpringBootApplication
public class SideCarHealthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SideCarHealthApplication.class, args);
	}
}

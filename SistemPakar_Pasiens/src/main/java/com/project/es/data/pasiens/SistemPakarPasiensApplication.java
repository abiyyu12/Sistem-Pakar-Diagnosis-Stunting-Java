package com.project.es.data.pasiens;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.project.es.data.libary","com.project.es.data.pasiens.*"})
@EnableJpaRepositories(value = "com.project.es.data.libary.repository")
@EntityScan(value = "com.project.es.data.libary.entity")
public class SistemPakarPasiensApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemPakarPasiensApplication.class, args);
	}

}

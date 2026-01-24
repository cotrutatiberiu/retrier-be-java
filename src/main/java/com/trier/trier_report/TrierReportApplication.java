package com.trier.trier_report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TrierReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrierReportApplication.class, args);
	}
}

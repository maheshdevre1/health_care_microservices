package com.health_care;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PatientManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientManagementServiceApplication.class, args);
	}

}

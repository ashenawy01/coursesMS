package com.sigma.coursesMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class CoursesMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoursesMsApplication.class, args);
	}

}

package com.clinic.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.clinic.project")
public class ProjectApplication {

public static void main(String[] args) {
    System.out.println("---- DATABASE ENVIRONMENT ----");
    System.out.println("URL: " + System.getenv("SPRING_DATASOURCE_URL"));
    System.out.println("USER: " + System.getenv("SPRING_DATASOURCE_USERNAME"));
    SpringApplication.run(ProjectApplication.class, args);
}

}

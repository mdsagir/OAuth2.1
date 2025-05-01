package org.example.edgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The EdgeServiceApplication class serves as the entry point for the Spring Boot
 * application. It is annotated with {@code @SpringBootApplication}, which signifies
 * it as a configuration class that declares one or more {@code @Bean} methods and
 * triggers automatic configuration and component scanning.
 * <p>
 * The {@code main} method is implemented to bootstrap and launch the application
 * using the {@code SpringApplication.run} method.
 */
@SpringBootApplication
public class EdgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeServiceApplication.class, args);
    }

}

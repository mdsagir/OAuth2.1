package org.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Main class for the Order Service Application.
 * <p>
 * This class serves as the entry point for the Spring Boot application and configuration.
 * It initializes and starts the Spring Boot framework, enabling the application to run.
 * <p>
 * The class is annotated with the following:
 * - {@code @SpringBootApplication}: Indicates a Spring Boot application with autoconfiguration, component scan, and configuration properties support.
 * - {@code @ConfigurationPropertiesScan}: Enables scanning and binding of external configuration properties to annotated Java objects.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}

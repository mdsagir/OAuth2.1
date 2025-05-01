package org.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the Catalog Service application.
 * <p>
 * This class serves as the bootstrap for the Spring Boot application. It
 * initializes and launches the application within the Spring Context.
 */
@SpringBootApplication
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

}

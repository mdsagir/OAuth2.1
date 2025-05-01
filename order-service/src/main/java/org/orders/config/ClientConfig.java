package org.orders.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * Configuration class for defining beans related to HTTP client setup.
 * This class is responsible for configuring and providing a {@link WebClient} bean
 * that can be used for making HTTP requests to external services.
 */
@Configuration
public class ClientConfig {
    /**
     * Creates and configures a {@link WebClient} bean for making HTTP requests.
     * The {@link WebClient} is initialized with a base URL derived from the catalog service URI
     * specified in the provided {@link ClientProperties}.
     *
     * @param clientProperties The configuration properties containing the catalog service URI.
     * @param webClientBuilder The builder used to construct the {@link WebClient} instance.
     * @return A configured {@link WebClient} instance with the base URL set to the catalog service URI.
     */
    @Bean
    public WebClient webClient(ClientProperties clientProperties, WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(clientProperties.catalogServiceUri().toString())
                .build();
    }
}

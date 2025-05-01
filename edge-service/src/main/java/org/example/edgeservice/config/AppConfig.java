package org.example.edgeservice.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Principal;

/**
 * Configuration class for application-specific beans and functionality.
 * Provides a KeyResolver bean to support request-rate-limiting strategies
 * based on user identification.
 */
@Configuration
public class AppConfig {

    /**
     * Provides a KeyResolver bean that determines a unique key for request-rate-limiting purposes.
     * The key is resolved based on the principal name associated with the current exchange.
     * If no principal is available, a default key of "anonymous" is used.
     *
     * @return a KeyResolver that resolves the key from the principal name or defaults to "anonymous"
     */
    @Bean
    public KeyResolver keyResolver() {
        return exchange -> exchange.getPrincipal()
                .map(Principal::getName)
                .defaultIfEmpty("anonymous");
    }
}

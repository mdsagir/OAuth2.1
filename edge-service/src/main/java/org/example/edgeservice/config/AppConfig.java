package org.example.edgeservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Configuration
public class AppConfig {

    @Bean
    public KeyResolver keyResolver() {
        return exchange-> exchange.getPrincipal()
                .map(principal -> {
                    return principal.getName();
                })
                .defaultIfEmpty("anonymous");
    }
}

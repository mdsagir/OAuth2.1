package com.edge_service.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Configuration
public class WebEndpoints {



    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/service-fallback", request -> ServerResponse.ok().body(Mono.just(""), String.class))
                .POST("/service-fallback", request -> ServerResponse.status(SERVICE_UNAVAILABLE).build()).build();
    }
    @Bean
    public KeyResolver keyResolver() {
        return exchange ->
                Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

}

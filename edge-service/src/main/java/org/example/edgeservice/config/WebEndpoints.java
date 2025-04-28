package org.example.edgeservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Configuration
public class WebEndpoints {
    private static final Logger log = LoggerFactory.getLogger(WebEndpoints.class);

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .GET("/catalog-fallback", request -> {
                    logRequestInfo(request);
                    return ServerResponse.ok().body(Mono.just(""), String.class);
                })
                .POST("/catalog-fallback", request -> {
                    logRequestInfo(request);
                    return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build();
                })
                .build();
    }

    private void logRequestInfo(ServerRequest request) {
        String path = request.path();
        String method = request.method().name();
        String userAgent = request.headers().firstHeader("User-Agent");
        String userId = request.headers().firstHeader("X-User-Id"); // Assume you pass user info in custom header

        log.debug("Incoming request: method={}, path={}, userAgent={}, userId={}",
                method, path, userAgent, userId != null ? userId : "anonymous");
    }
}
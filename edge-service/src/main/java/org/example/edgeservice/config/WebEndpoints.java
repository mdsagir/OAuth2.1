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

/**
 * Configuration class for defining web endpoints in a reactive web application.
 * This class provides a router function bean that handles specific HTTP requests.
 */
@Configuration
public class WebEndpoints {
    private static final Logger log = LoggerFactory.getLogger(WebEndpoints.class);

    /**
     * Defines a router function for handling HTTP GET and POST requests to the "/catalog-fallback" endpoint.
     * The GET request returns a 200-OK response with an empty string body, and the POST request returns a 503 Service Unavailable response.
     * Logs metadata of incoming requests for both GET and POST requests.
     *
     * @return a RouterFunction that maps HTTP requests to appropriate handlers for the "/catalog-fallback" endpoint
     */
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

    /**
     * Logs metadata of an incoming HTTP request, including the HTTP method, request path,
     * User-Agent header, and a custom X-User-Id header if present.
     *
     * @param request the incoming {@link ServerRequest} containing information about the HTTP request
     */
    private void logRequestInfo(ServerRequest request) {
        String path = request.path();
        String method = request.method().name();
        String userAgent = request.headers().firstHeader("User-Agent");
        String userId = request.headers().firstHeader("X-User-Id"); // Assume you pass user info in a custom header

        log.debug("Incoming request: method={}, path={}, userAgent={}, userId={}",
                method, path, userAgent, userId != null ? userId : "anonymous");
    }
}

package com.order_service.config;

import com.order_service.inceptor.JwtRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class Config {

    @Bean
    public RestClient restClient() {
        return RestClient.builder().requestInterceptor(new JwtRequestInterceptor()).build();

    }
}

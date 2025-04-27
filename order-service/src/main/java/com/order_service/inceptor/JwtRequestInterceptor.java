package com.order_service.inceptor;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

public class JwtRequestInterceptor  implements ClientHttpRequestInterceptor {

    @Override
    @NonNull
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            String jwtToken = attributes.getRequest().getHeader(HttpHeaders.AUTHORIZATION);
            if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
                request.getHeaders().add(HttpHeaders.AUTHORIZATION, jwtToken);  // Pass it along with the Feign request
            }
        }

        return execution.execute(request, body);
    }
}

package com.order_service.client;


import com.order_service.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RestClientService {

    private final RestClient restClient;

    public RestClientService(RestClient restClient) {
        this.restClient = restClient;
    }

    public User getUserById(Long id) {
        ResponseEntity<User> entity = restClient.get().uri("http://localhost:8080/",

                        uriBuilder -> uriBuilder.path("/user/" + id).build())
                .retrieve().toEntity(User.class);
        return entity.getBody();
    }
}

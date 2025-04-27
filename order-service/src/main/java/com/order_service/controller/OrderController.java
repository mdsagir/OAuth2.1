package com.order_service.controller;


import com.order_service.client.RestClientService;
import com.order_service.model.Order;
import com.order_service.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("order")
public class OrderController {

    private final RestClientService restClientService;
    private final List<Order> list = new ArrayList<>();

    public OrderController(RestClientService restClientService) {
        this.restClientService = restClientService;
    }


    @PostMapping
    public Order createUser(@RequestBody Order order) {
        list.add(order);
        return order;
    }

    @GetMapping
    public List<Order> getAllUser() {
        return list;
    }

    @GetMapping("/{id}")
    public Order getUser(@PathVariable Long id) {
        Order order = list.stream().filter(user -> Objects.equals(user.orderId(), id)).findFirst().orElse(null);
        validateNull(order, "Order is not available");
        User user = restClientService.getUserById(order.userId());
        validateNull(user, "User is not found");
        return order;

    }

    private void validateNull(Object object, String errMessage) {
        if (object == null) {
            throw new RuntimeException(errMessage);
        }
    }
}


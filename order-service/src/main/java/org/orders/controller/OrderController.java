package org.orders.controller;

import org.orders.model.Order;
import org.orders.model.OrderRequest;
import org.orders.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/orders")
public class OrderController {

    /**
     * Logger instance used for logging messages within the {@link OrderController} class.
     * It provides functionality for error reporting, informational messages, and debugging
     * related to the operations performed by the controller.
     */
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    /**
     * Service layer dependency handling order-related operations which this controller delegates to.
     * This instance is responsible for processing requests related to orders, including retrieving
     * all orders and submitting new orders. It communicates with other services such as BookClient
     * to fetch necessary data and processes business logic associated with order acceptance or rejection.
     *
     * Used within {@link OrderController} to abstract and organize business logic related
     * to handling orders.
     */
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieves all existing orders.
     *
     * @return a list of all orders available, represented as {@link Order} objects.
     */
    @GetMapping
    List<Order> getAllOrders() {
        log.debug("Retrieving all orders");
        return orderService.getAllOrders();
    }

    /**
     * Submits a new order based on the provided {@code OrderRequest}.
     * This method delegates the order creation logic to the service layer,
     * where it determines whether the order is accepted or rejected based on
     * the availability of the book and other criteria.
     *
     * @param orderRequest the order request containing the ISBN of the book and the quantity to order
     * @return a {@link Mono} that emits the {@link Order} created, which may either be accepted or rejected
     */
    @PostMapping
    public Mono<Order> submitOrder(@RequestBody OrderRequest orderRequest) {
        log.debug("Submitting order for ISBN: {}", orderRequest.isbn());
        return orderService.submitOrder(orderRequest);
    }
}

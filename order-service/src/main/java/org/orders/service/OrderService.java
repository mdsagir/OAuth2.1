package org.orders.service;

import org.orders.client.BookClient;
import org.orders.model.Book;
import org.orders.model.Order;
import org.orders.model.OrderRequest;
import org.orders.model.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    /**
     * A client instance used to interact with the external book service.
     * It serves as the primary means of retrieving book information, such as fetching
     * details about a book using its ISBN. This dependency abstracts the underlying
     * communication with the external service, facilitating seamless integration
     * and data retrieval required for processing orders.
     */
    private final BookClient bookClient;

    /**
     * A map that stores and manages all orders in the system. The key is a unique
     * {@link Long} identifier for each order, and the value is an instance of {@link Order},
     * representing the details of the order. This map serves as the primary in-memory
     * data store for tracking and accessing orders created or processed by the system.
     */
    private final Map<Long, Order> ordersMap = new java.util.HashMap<>();

    public OrderService(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    /**
     * Retrieves a list of all orders stored in the system.
     *
     * @return a list containing all {@link Order} objects currently managed in the system.
     */
    public List<Order> getAllOrders() {
        List<Order> list = ordersMap.values().stream().toList();
        log.debug("Retrieved {} orders", list.size());
        return list;
    }

    /**
     * Processes an order submission request based on the provided {@code OrderRequest}.
     * The method interacts with an external book service to retrieve book details,
     * creates an order (either accepted or rejected), and stores it in the internal orders map.
     *
     * @param orderRequest the order request containing the ISBN of the book and the quantity to order
     * @return a {@link Mono} that emits the created {@link Order}, which can be an
     * accepted or rejected order depending on the book's availability
     */
    public Mono<Order> submitOrder(OrderRequest orderRequest) {
        log.debug("Submitting order for ISBN: {}", orderRequest.isbn());
        return bookClient.getBookByIsbn(orderRequest.isbn()).
                map(book -> buildAcceptedOrder(book, orderRequest.quantity()))
                .switchIfEmpty(Mono.defer(() -> Mono.just(buildRejectedOrder(orderRequest.isbn(), orderRequest.quantity()))))
                .mapNotNull(order -> {
                     ordersMap.put(order.id(), order);
                     log.debug("Order created with ID: {}", order.id());
                     return order;
                });
    }

    /**
     * Builds a rejected order with the specified book ISBN and quantity.
     * This method creates an order with a status of {@code OrderStatus.REJECTED}
     * when the book availability validation fails.
     *
     * @param bookIsbn the ISBN of the book being ordered
     * @param quantity the quantity of the book being ordered
     * @return an {@link Order} instance representing the rejected order
     */
    public static Order buildRejectedOrder(String bookIsbn, int quantity) {
        log.debug("Book with ISBN {} is not available", bookIsbn);
        return Order.of(bookIsbn, null, null, quantity, OrderStatus.REJECTED);
    }

    /**
     * Builds an accepted order based on the provided book and quantity.
     * This method constructs an {@link Order} with a status of {@code OrderStatus.ACCEPTED}.
     *
     * @param book     the {@link Book} instance containing details of the book for the order
     * @param quantity the quantity of the book to include in the order
     * @return an {@link Order} instance representing the accepted order
     */
    public static Order buildAcceptedOrder(Book book, int quantity) {
        log.debug("Book with ISBN {} is available", book.isbn());
        return Order.of(book.isbn(), book.title() + " - " + book.author(),
                book.price(), quantity, OrderStatus.ACCEPTED);
    }
}

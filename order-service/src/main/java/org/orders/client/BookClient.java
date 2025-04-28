package org.orders.client;

import org.orders.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class BookClient {
    /**
     * Represents the root API endpoint for books-related operations.
     * This base URI acts as the starting point for book-specific REST API requests.
     */
    private static final String BOOKS_ROOT_API = "/books/";
    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(BookClient.class);


    public BookClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Book> getBookByIsbn(String isbn) {
        log.debug("Retrieving book with ISBN: {}", isbn);
        return webClient
                .get()
                .uri(BOOKS_ROOT_API + isbn)
                .retrieve()
                .bodyToMono(Book.class)
                .timeout(Duration.ofSeconds(3), Mono.empty())
                .onErrorResume(WebClientResponseException.NotFound.class, exception -> {
                    log.error("Book with ISBN {} not found", isbn);
                    return Mono.empty();
                })
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100)))
                .onErrorResume(Exception.class, exception -> {
                    log.error("Error retrieving book with ISBN {}", isbn, exception);
                    return Mono.empty();
                });
    }
}

package org.orders.model;

/**
 * Represents a request to create an order for a specific book, encapsulating the book's
 * ISBN and the desired quantity.
 * <p>
 * This record is designed as a simple data holder for order-related input,
 * providing immutable storage for the values specified at instantiation.
 * <p>
 * Key characteristics:
 * - The `isbn` field represents the International Standard Book Number of the book to be ordered.
 * - The `quantity` field specifies the number of copies requested for the order.
 * <p>
 * Instances of this class are primarily used as transfer objects between
 * layers of the application to capture order details provided by users.
 */
public record OrderRequest(String isbn, Integer quantity) {
}

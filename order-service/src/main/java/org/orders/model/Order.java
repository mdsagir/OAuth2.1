package org.orders.model;

import java.time.Instant;
import java.util.Random;

/**
 * Represents an Order entity that includes details about a requested book, such as
 * its ISBN, name, price, quantity ordered, and the current status of the order.
 * Additional metadata includes the creation and last modification timestamps, as well as
 * a version number to track updates.
 * <p>
 * This record is immutable and designed to encapsulate the state of an order, providing
 * readonly access to its attributes once instantiated. It also includes utility methods for
 * order creation and unique ID generation.
 * <p>
 * Key characteristics:
 * - Includes attributes for book-related details and order metadata.
 * - Uses a static factory method for convenient creation of Order instances.
 * - Contains a utility method to generate a unique identifier for orders using
 *   the current system time and a random component.
 * <p>
 * Static Methods:
 * - of: Creates a new Order instance with specified attributes.
 * - generate: Produces a unique identifier based on the current time and a random number.
 * <p>
 * This design ensures thread safety and immutability for instances of the Order record.
 *
 * @param id                The unique identifier for the order.
 * @param bookIsbn          The ISBN of the book associated with the order.
 * @param bookName          The name of the book being ordered.
 * @param bookPrice         The price of the book being ordered.
 * @param quantity          The quantity of books requested in the order.
 * @param status            The current status of the order (e.g., ACCEPTED, REJECTED).
 * @param crateDate         The timestamp when the order was created.
 * @param lastModifiedDate  The timestamp when the order was last modified.
 * @param version           The version of the order record, used for concurrency control.
 */
public record Order(
        Long id,
        String bookIsbn,
        String bookName,
        Double bookPrice,
        Integer quantity,
        OrderStatus status,
        Instant crateDate,
        Instant lastModifiedDate,
        Integer version
) {
    private static final Random RANDOM = new Random();

    public static Order of(String bookIsbn, String bookName, Double bookPrice, Integer quantity, OrderStatus status) {
        return new Order(generate(), bookIsbn, bookName, bookPrice, quantity, status, null, null, 0);
    }

    /**
     * Generates a unique long value by combining the current system time in milliseconds
     * and a random number. The generated value is intended to serve as a unique identifier.
     *
     * @return A unique long value generated using the current system time and a random component.
     */
    public static long generate() {
        long currentTimeMillis = System.currentTimeMillis();
        int randomPart = RANDOM.nextInt(999); // random 0-998
        return (currentTimeMillis % Integer.MAX_VALUE) + randomPart;
    }
}

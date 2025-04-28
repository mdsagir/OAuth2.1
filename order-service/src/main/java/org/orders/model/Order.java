package org.orders.model;

import java.time.Instant;
import java.util.Random;

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
    public static Order of(String bookIsbn, String bookName, Double bookPrice, Integer quantity, OrderStatus status) {
        return new Order(generate(), bookIsbn, bookName, bookPrice, quantity, status, null, null, 0);
    }

    public static long generate() {
        final Random random = new Random();
        long currentTimeMillis = System.currentTimeMillis();
        int randomPart = random.nextInt(999); // random 0-998
        return (currentTimeMillis % Integer.MAX_VALUE) + randomPart;
    }
}

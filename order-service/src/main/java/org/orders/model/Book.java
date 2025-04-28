package org.orders.model;

import java.time.Instant;

public record Book(
        Long id,
        String isbn,
        String title,
        String author,
        Double price,
        String publisher,
        Instant createdDate,
        Instant lastModifiedDate,
        String createdBy,
        String lastModifiedBy,
        int version
) {
    public static Book of(String isbn, String title, String author, Double price, String publisher) {
        return new Book(null, isbn, title, author, price, publisher, null, null, null, null, 0);
    }
}

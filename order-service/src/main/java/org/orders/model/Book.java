package org.orders.model;

import java.time.Instant;

/**
 * Represents a Book entity with comprehensive details such as ID, ISBN, title, author,
 * price, publisher, creation and modification metadata, and a version for concurrency control.
 * This record is immutable and intended as a data holder for book-related information.
 * <p>
 * Key characteristics include:
 * - Unique identifier for each book (id).
 * - ISBN representing the standard book number for identification.
 * - Other descriptive fields like title, author, and price.
 * - Metadata like created and last modified timestamps, along with associated user information.
 * - Versioning property for tracking updates.
 * <p>
 * The class also provides a static factory method for convenient creation of Book instances
 * with essential attributes.
 *
 * @param id                The unique identifier for the book.
 * @param isbn              The International Standard Book Number for the book.
 * @param title             The title of the book.
 * @param author            The author of the book.
 * @param price             The price of the book.
 * @param publisher         The publisher of the book.
 * @param createdDate       The timestamp when the book entry was created.
 * @param lastModifiedDate  The timestamp when the book entry was last modified.
 * @param createdBy         The identifier of the user who created the book entry.
 * @param lastModifiedBy    The identifier of the user who last modified the book entry.
 * @param version           The version of the book entry, used for concurrency control.
 */
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
    /**
     * Creates a new instance of the Book record with the specified attributes.
     *
     * @param isbn      The International Standard Book Number for the book.
     * @param title     The title of the book.
     * @param author    The author of the book.
     * @param price     The price of the book.
     * @param publisher The publisher of the book.
     * @return A new Book instance with the specified isbn, title, author, price, and publisher,
     *         along with default values for other attributes such as id, creation metadata,
     *         and version.
     */
    public static Book of(String isbn, String title, String author, Double price, String publisher) {
        return new Book(null, isbn, title, author, price, publisher, null, null, null, null, 0);
    }
}

package org.example.edgeservice.model;

import java.util.List;

/**
 * Represents a user of the system with associated details.
 * <p>
 * This record encapsulates user information including their username, personal names,
 * and the set of roles assigned to them.
 * <p>
 * It is primarily intended for use in scenarios where user data needs to be transferred
 * between application layers or communicated to clients.
 *
 * @param username the unique username for the user
 * @param firstName the first (given) name of the user
 * @param lastName the last (family) name of the user
 * @param roles the list of roles assigned to the user
 */
public record User(String username,
                   String firstName,
                   String lastName,
                   List<String> roles) {
}

package org.orders.model;

/**
 * Represents the status of an order, indicating whether the order has been accepted
 * or rejected. This enum is used to define the possible states of an order throughout
 * its lifecycle.
 * <p>
 * Enumerated values:
 * - ACCEPTED: Indicates that the order has been successfully accepted.
 * - REJECTED: Indicates that the order has been declined.
 * <p>
 * This enum is typically used in conjunction with the Order record to specify the
 * current state of the order.
 */
public enum OrderStatus {
    ACCEPTED,REJECTED
}

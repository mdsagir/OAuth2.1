package org.catalog.util;

import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * Utility class providing logging functionality for debug operations.
 */
public class Log {

    private Log(){}

    /**
     * Logs a debug-level message using the provided logger if debug logging is enabled.
     *
     * @param logger the logger instance used to log the debug message
     * @param messageSupplier a supplier providing the log message to be generated and logged
     */
    public static void debug(Logger logger, Supplier<String> messageSupplier) {
        if (logger.isDebugEnabled()) {
            logger.debug(messageSupplier.get());
        }
    }
}

package org.catalog.util;

import org.slf4j.Logger;

import java.util.function.Supplier;

public class Log {
    public static void debug(Logger logger, Supplier<String> messageSupplier) {
        if (logger.isDebugEnabled()) {
            logger.debug(messageSupplier.get());
        }
    }
}

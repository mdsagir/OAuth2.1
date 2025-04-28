package org.orders.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

@ConfigurationProperties(prefix = "alpha")
public record ClientProperties( URI catalogServiceUri) {
}

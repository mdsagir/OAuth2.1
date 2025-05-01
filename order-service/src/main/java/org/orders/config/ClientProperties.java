package org.orders.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URI;

/**
 * Configuration properties for the client setup.
 * This class encapsulates properties prefixed with "alpha" in the application's
 * configuration files, such as application.yml or application.properties.
 * <p>
 * It is used for defining and retrieving the URI of the catalog service, which
 * acts as the base URL for HTTP requests made by the application.
 * <p>
 * The {@link ClientProperties} is typically injected as a dependency into beans
 * that rely on this configuration, such as HTTP clients.
 *
 * @param catalogServiceUri The URI of the catalog service.
 */
@ConfigurationProperties(prefix = "alpha")
public record ClientProperties( URI catalogServiceUri) {
}

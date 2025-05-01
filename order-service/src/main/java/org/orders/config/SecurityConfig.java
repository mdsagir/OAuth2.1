package org.orders.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;

/**
 * Configuration class for setting up security configurations in a reactive web application.
 * This class defines security-related beans and customizes the application's security behavior.
 * <p>
 * It leverages Spring Security's WebFlux support to define reactive security configurations,
 * such as securing endpoints, integrating with an OAuth2 resource server, and disabling
 * CSRF protection for simplicity.
 */
@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
public class SecurityConfig {

	/**
	 * Configures and builds the security filter chain for a reactive web application.
	 * This method defines the security settings, such as endpoint authorization rules,
	 * OAuth2 JWT resource server integration, disabling CSRF protection, and customizing
	 * request caching behavior.
	 *
	 * @param http The {@link ServerHttpSecurity} object used to configure security settings.
	 * @return A {@link SecurityWebFilterChain} that represents the security configuration
	 *         for the application.
	 */
	@Bean
	SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
		return http
				.authorizeExchange(exchange -> exchange
						.pathMatchers("/actuator/**").permitAll()
						.anyExchange().authenticated()
				)
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.requestCache(requestCacheSpec ->
						requestCacheSpec.requestCache(NoOpServerRequestCache.getInstance()))
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.build();
	}

}

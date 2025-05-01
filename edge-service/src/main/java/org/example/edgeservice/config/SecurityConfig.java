package org.example.edgeservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.WebSessionServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.XorServerCsrfTokenRequestAttributeHandler;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * Configuration class for setting up Spring Security in a reactive web application.
 * This class enables security features including OAuth2 login, logout handling,
 * CSRF protection, and path-based access control. It configures security filters
 * and related beans to customize authentication*/
@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
public class SecurityConfig {


    /**
     * Configures the Spring Security filter chain for a reactive web application,
     * defining security rules, authentication and logout behavior, and CSRF protection.
     * Allows definition of paths that require authentication or are publicly accessible.
     *
     * @param http the {@link ServerHttpSecurity} object used to configure security settings
     * @param clientRegistrationRepository the {@link ReactiveClientRegistrationRepository} used for
     *                                      managing OAuth2 client registrations
     * @return the configured {@link SecurityWebFilterChain} instance
     */
    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository) {
        return http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/", "/*.css", "/*.js", "/favicon.ico").permitAll()
                        .pathMatchers(HttpMethod.GET, "/books/**").permitAll()
                        .anyExchange().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout.logoutSuccessHandler(oidcLogoutSuccessHandler(clientRegistrationRepository)))
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new XorServerCsrfTokenRequestAttributeHandler()::handle))
                .build();
    }

    /**
     * Creates a {@link ServerLogoutSuccessHandler} that facilitates OpenID Connect (OIDC) client-initiated
     * logout. The handler manages the redirection behavior after a successful OIDC logout.
     * <p>
     * The post-logout redirection URI is set to the base URL of the application.
     *
     * @param clientRegistrationRepository the {@link ReactiveClientRegistrationRepository} used for
     *                                     managing OAuth2 client registrations
     * @return an instance of {@link ServerLogoutSuccessHandler} configured for OIDC logout
     */

    private ServerLogoutSuccessHandler oidcLogoutSuccessHandler(ReactiveClientRegistrationRepository clientRegistrationRepository) {
        var oidcLogoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");
        return oidcLogoutSuccessHandler;
    }

    /**
     * Creates a {@link WebFilter} that ensures CSRF protection by processing and committing CSRF tokens
     * before the server response is finalized. This implementation resolves the potential issue described
     * in https://github.com/spring-projects/spring-security/issues/5766.
     *
     * @return a {@link WebFilter} that supports CSRF token handling for reactive web applications
     */
    @Bean
    WebFilter csrfWebFilter() {
        // Required because of https://github.com/spring-projects/spring-security/issues/5766
        return (exchange, chain) -> {
            exchange.getResponse().beforeCommit(() -> Mono.defer(() -> {
                Mono<CsrfToken> csrfToken = exchange.getAttribute(CsrfToken.class.getName());
                return csrfToken != null ? csrfToken.then() : Mono.empty();
            }));
            return chain.filter(exchange);
        };
    }

    /**
     * Creates and provides a {@link ServerOAuth2AuthorizedClientRepository} bean to manage
     * the persistence of authorized client information for use in a reactive OAuth 2.0 context.
     * <p>
     * This implementation stores the authorized client data in the web session,
     * allowing it to associate OAuth 2.0 authorized clients with a user's session.
     *
     * @return a {@link ServerOAuth2AuthorizedClientRepository} that persists authorized client data in the web session
     */
    @Bean
    ServerOAuth2AuthorizedClientRepository authorizedClientRepository() {
        return new WebSessionServerOAuth2AuthorizedClientRepository();
    }


}

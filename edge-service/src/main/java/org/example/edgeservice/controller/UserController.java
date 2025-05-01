package org.example.edgeservice.controller;

import org.example.edgeservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * A REST controller that provides an endpoint for retrieving user details.
 * <p>
 * This controller handles user-related requests by extracting user information
 * from an authenticated OIDC user and returning it as a reactive Mono<User>.
 */
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    /**
     * Retrieves the details of the currently authenticated user.
     * <p>
     * This method extracts user details from the authenticated OIDC user and
     * returns them wrapped in a reactive Mono container.
     *
     * @param oidcUser the currently authenticated OIDC user, injected by Spring Security
     * @return a Mono containing the details of the authenticated user
     */
    @GetMapping("user")
    public Mono<User> getUser(@AuthenticationPrincipal OidcUser oidcUser) {
        var user = new User(
                oidcUser.getPreferredUsername(),
                oidcUser.getGivenName(),
                oidcUser.getFamilyName(),
                oidcUser.getClaimAsStringList("roles")
        );
        log.debug("User: {}", user);
        return Mono.just(user);
    }
}


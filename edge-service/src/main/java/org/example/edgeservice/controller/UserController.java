package org.example.edgeservice.controller;

import org.example.edgeservice.config.AppConfig;
import org.example.edgeservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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


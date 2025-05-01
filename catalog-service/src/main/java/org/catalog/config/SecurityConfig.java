package org.catalog.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

	/**
	 * Configures the security filter chain for the application.
	 *
	 * @param http the {@link HttpSecurity} instance used to configure HTTP security
	 *             for the application
	 * @return a configured instance of {@link SecurityFilterChain} that includes
	 *         authorization rules, OAuth2 resource server configuration, stateless
	 *         session management, and CSRF protection disabled
	 * @throws Exception if an error occurs while configuring the security filter chain
	 */
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/actuator/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/", "/books/**").permitAll()
						.anyRequest().hasRole("employee")
				)
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.sessionManagement(sessionManagement ->
						sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(AbstractHttpConfigurer::disable)
				.build();
	}

	/**
	 * Provides a {@link JwtAuthenticationConverter} bean to convert JWT claims into
	 * Spring Security granted authorities. Configures the {@link JwtGrantedAuthoritiesConverter}
	 * to use a specific authority prefix and a custom claim name for role mapping.
	 *
	 * @return a configured {@link JwtAuthenticationConverter} that maps roles from
	 * the "roles" claim with the "ROLE_" prefix
	 */
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

}

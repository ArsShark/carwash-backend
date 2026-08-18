package com.example.carwash.config;

import com.example.carwash.security.JwtAuthenticationFilter;
import com.example.carwash.security.RestAccessDeniedHandler;
import com.example.carwash.security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Wires up stateless JWT authentication for the whole API. GET requests to
 * the four business resources are open to any authenticated user; write
 * operations are gated per-endpoint via {@code @PreAuthorize} on the
 * controllers rather than here, so this class only needs to require
 * authentication for {@code /api/**} in general. {@link
 * RestAuthenticationEntryPoint} and {@link RestAccessDeniedHandler} make
 * sure failures at this filter-chain level get the same JSON error shape
 * as failures handled inside Spring MVC by {@code GlobalExceptionHandler}.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (registration/login only — /api/auth/me
                        // needs an authenticated caller, see the matcher below)
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        // Swagger/OpenAPI (optional)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        // GET requests - available to any authenticated user (USER and ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/clients/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/services/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/appointments/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/branches/**").hasAnyRole("USER", "ADMIN")
                        // Write operations (POST/PUT/DELETE) are gated per-endpoint by @PreAuthorize
                        // in the controllers; here we only require the caller to be authenticated.
                        .requestMatchers("/api/**").authenticated()
                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

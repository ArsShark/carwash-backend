package com.example.carwash.config;

import com.example.carwash.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Публичные эндпоинты (аутентификация)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        // Swagger/OpenAPI (опционально)
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        // GET запросы - доступны авторизованным пользователям (USER и ADMIN)
                        .requestMatchers("/api/clients/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/services/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/appointments/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/branches/**").hasAnyRole("USER", "ADMIN")
                        // POST, PUT, DELETE - только ADMIN
                        .requestMatchers("/api/**").hasRole("ADMIN")
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
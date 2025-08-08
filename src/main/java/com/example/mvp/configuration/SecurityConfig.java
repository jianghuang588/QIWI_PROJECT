package com.example.mvp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // API endpoints
                        .requestMatchers("/api/**").permitAll()

                        // Web pages - Public pages (no login required)
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/terms").permitAll()
                        .requestMatchers("/privacy").permitAll()
                        .requestMatchers("/universities").permitAll()
                        .requestMatchers("/how-it-works").permitAll()
                        .requestMatchers("/about").permitAll()

                        // Web pages - User required
                        .requestMatchers("/role-selection").permitAll()
                        .requestMatchers("/dashboard").permitAll()
                        .requestMatchers("/tutor-profile").permitAll()
                        .requestMatchers("/tutee-profile").permitAll()
                        .requestMatchers("/browse-tutors").permitAll()
                        .requestMatchers("/browse-tutees").permitAll()
                        .requestMatchers("/messages").permitAll()

                        // Static resources
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()

                        // Development tools
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/actuator/**").permitAll()

                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions().disable());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
package org.school.stylish.config;

import org.school.stylish.filter.AuthenticationErrorFilter;
import org.school.stylish.filter.JwtAuthenticationFilter;
import org.school.stylish.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final JwtUtil jwtUtil;

    public SpringSecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF.
                .csrf(csrf -> csrf.disable())
                // Start configuring HTTP request authorization.
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers(routing) || permitAll() -> Allow all requests
                        // config api security.
                        .requestMatchers("/api/1.0/products/**").permitAll()
                        .requestMatchers("/api/1.0/user/signup", "/api/1.0/user/signin").permitAll()
                        .requestMatchers("/api/1.0/marketing/**").permitAll()
                        .requestMatchers("/api/1.0/order/**").permitAll()
                        .requestMatchers("/api/1.0/report/**").permitAll()
                        .requestMatchers("/api/2.0/report/**").permitAll()
                        // config static sources security.
                        .requestMatchers("/static/**", "/css/**", "/javascript/**", "/images/**").permitAll()
                        .requestMatchers("/**").permitAll()
                        // Require authentication for the specified path.
                        .requestMatchers("/api/1.0/user/profile", "/api/1.0/order/checkout").authenticated()
                        // Require ADMIN role for the specified path.
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // All other requests require authentication.
                        .anyRequest().authenticated())
                // Add custom filters.
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new AuthenticationErrorFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

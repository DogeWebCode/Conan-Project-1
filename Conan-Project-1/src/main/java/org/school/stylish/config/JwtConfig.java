package org.school.stylish.config;

import org.school.stylish.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil(secretKey, expirationTime);
    }
}

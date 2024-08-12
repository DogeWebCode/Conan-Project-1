package org.school.stylish.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.school.stylish.dto.user.ProfileDTO;
import org.school.stylish.dto.user.RoleDTO;
import org.school.stylish.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final JwtParser jwtParser;
    @Getter
    private final long expirationTime;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long expirationTime) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        this.expirationTime = expirationTime;
    }

    public String generateToken(UserDTO user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("provider", user.getProvider());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        claims.put("picture", user.getPicture());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateTokenWithRoles(UserDTO userDetails, Set<RoleDTO> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getEmail());
        claims.put("roles", roles.stream().map(RoleDTO::getName).collect(Collectors.toList()));
        return createToken(claims);
    }

    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Claims getClaimsFromToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            // Invalid JWT signature
        } catch (MalformedJwtException ex) {
            // Invalid JWT token
        } catch (ExpiredJwtException ex) {
            // JWT token has expired
        } catch (UnsupportedJwtException ex) {
            // Unsupported JWT token
        } catch (IllegalArgumentException ex) {
            // JWT token is empty
        }
        return false;
    }

    public ProfileDTO CreateUserFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return ProfileDTO.builder()
                .provider(claims.get("provider", String.class))
                .name(claims.get("name", String.class))
                .email(claims.getSubject())
                .picture(claims.get("picture", String.class))
                .build();
    }

    public Long extractUserId(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("id", Long.class);
    }

    public List<String> extractRoles(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("roles", List.class);
    }
}

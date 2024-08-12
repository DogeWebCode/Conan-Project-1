package org.school.stylish.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.school.stylish.util.JwtUtil;
import org.school.stylish.dto.user.ProfileDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/static/") ||
                path.startsWith("/css/") ||
                path.startsWith("/javascript/") ||
                path.startsWith("/page/") ||
                path.startsWith("/images/") ||
                path.startsWith("/api/1.0/products/") ||
                path.startsWith("/api/1.0/marketing/") ||
                path.startsWith("/api/1.0/report/") ||
                path.startsWith("/api/2.0/report/") ||
                path.equals("/") ||
                path.equals("/index.html") ||
                path.equals("/product.html") ||
                path.equals("/profile.html") ||
                path.equals("/thankyou.html") ||
                path.equals("/api/1.0/user/signin") ||
                path.equals("/api/1.0/user/signup");

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Filtering request: {}", request.getRequestURI());
        try {
            String token = extractToken(request);
            if (token == null) {
                // No token provided
                SecurityContextHolder.clearContext();
                request.setAttribute("authError", "No token");
                request.setAttribute("statusCode", HttpServletResponse.SC_UNAUTHORIZED);
            } else if (!jwtUtil.validateToken(token)) {
                // Invalid token
                SecurityContextHolder.clearContext();
                request.setAttribute("authError", "Wrong token");
                request.setAttribute("statusCode", HttpServletResponse.SC_FORBIDDEN);
            } else {
                // Valid token, proceed with authentication
                Long userId = jwtUtil.extractUserId(token);
                ProfileDTO user = jwtUtil.CreateUserFromToken(token);
                List<String> roles = jwtUtil.extractRoles(token);
                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
                        .toList();
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, userId, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            request.setAttribute("authError", "Server error");
            request.setAttribute("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            log.error("Authentication error", e);
        }
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

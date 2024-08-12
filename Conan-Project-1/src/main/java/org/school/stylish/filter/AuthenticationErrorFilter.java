package org.school.stylish.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthenticationErrorFilter extends OncePerRequestFilter {

    /**
     * Internal method to perform filtering.
     *
     * @param request     The HTTP request
     * @param response    The HTTP response
     * @param filterChain The filter chain for additional processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Check if there's an authentication error
        if (request.getAttribute("authError") != null) {
            // Extract error details from the request attributes
            int statusCode = (int) request.getAttribute("statusCode");
            // Handle the error and return
            String errorMessage = (String) request.getAttribute("authError");
            // If no error, continue with the filter chain
            handleError(response, statusCode, errorMessage);
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Handles authentication errors by setting appropriate response status and content.
     *
     * @param response   The HTTP response
     * @param statusCode The HTTP status code to set
     * @param message    The error message
     */
    private void handleError(HttpServletResponse response, int statusCode, String message) throws IOException {
        // Set the HTTP status code
        response.setStatus(statusCode);
        // Set content type to JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Create a JSON string with the error message
        String json = String.format("{\"error\": \"%s\"}", message);
        // Write the JSON to the response
        response.getWriter().write(json);
    }
}
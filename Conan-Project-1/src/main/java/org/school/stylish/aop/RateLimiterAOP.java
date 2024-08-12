package org.school.stylish.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.school.stylish.util.RateLimiterUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimiterAOP {
    private final RateLimiterUtil rateLimiterUtil;
    private final HttpServletRequest request;

    @Before("within(org.school.stylish.controller.*)")
    public void rateLimit() {
        String ip = request.getRemoteAddr();
        if (!rateLimiterUtil.allowRequest(ip)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS);
        }
    }
}

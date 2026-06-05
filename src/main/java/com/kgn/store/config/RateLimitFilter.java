package com.kgn.store.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Lightweight fixed-window rate limit on write endpoints (POST), to blunt
 * abuse/spam of the public enquiry and order forms. In-memory and per-instance
 * (good enough for a single small free-tier instance). For multi-instance
 * deployments, move this to a shared store such as Redis.
 */
@Component
@Order(3)
public class RateLimitFilter extends OncePerRequestFilter {

    private final int maxPerMinute;
    private final Map<String, Window> windows = new ConcurrentHashMap<>();

    public RateLimitFilter(@Value("${kgn.rate-limit.requests-per-minute}") int maxPerMinute) {
        this.maxPerMinute = maxPerMinute;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        if ("POST".equalsIgnoreCase(req.getMethod())) {
            String ip = clientIp(req);
            long minute = System.currentTimeMillis() / 60_000L;
            Window w = windows.compute(ip, (k, cur) -> {
                if (cur == null || cur.minute != minute) return new Window(minute);
                return cur;
            });
            if (w.count.incrementAndGet() > maxPerMinute) {
                res.setStatus(429);
                res.setContentType("application/json");
                res.getWriter().write("{\"error\":\"rate_limited\",\"message\":\"Too many requests, please slow down\"}");
                return;
            }
            if (windows.size() > 10_000) windows.clear(); // crude memory guard
        }
        chain.doFilter(req, res);
    }

    private String clientIp(HttpServletRequest req) {
        String xff = req.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) return xff.split(",")[0].trim();
        return req.getRemoteAddr();
    }

    private static final class Window {
        final long minute;
        final AtomicInteger count = new AtomicInteger(0);
        Window(long minute) { this.minute = minute; }
    }
}

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
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Protects /api/admin/** endpoints.
 *
 * Fail-secure: if ADMIN_API_KEY is not configured, every admin request is
 * rejected. The key is compared in constant time to avoid timing attacks.
 */
@Component
@Order(2)
public class AdminKeyFilter extends OncePerRequestFilter {

    private final String adminKey;

    public AdminKeyFilter(@Value("${kgn.admin-api-key}") String adminKey) {
        this.adminKey = adminKey == null ? "" : adminKey.trim();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {
        String path = req.getRequestURI();
        if (path != null && path.startsWith("/api/admin")) {
            if (adminKey.isEmpty()) {
                deny(res, "Admin access is not configured");
                return;
            }
            String provided = req.getHeader("X-Admin-Key");
            if (provided == null || !constantTimeEquals(provided, adminKey)) {
                deny(res, "Invalid admin key");
                return;
            }
        }
        chain.doFilter(req, res);
    }

    private void deny(HttpServletResponse res, String msg) throws IOException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setContentType("application/json");
        res.getWriter().write("{\"error\":\"forbidden\",\"message\":\"" + msg + "\"}");
    }

    private boolean constantTimeEquals(String a, String b) {
        return MessageDigest.isEqual(
                a.getBytes(StandardCharsets.UTF_8),
                b.getBytes(StandardCharsets.UTF_8));
    }
}

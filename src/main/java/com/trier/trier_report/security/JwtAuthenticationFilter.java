package com.trier.trier_report.security;

import com.trier.trier_report.util.CsrfTokenUtil;
import com.trier.trier_report.util.JwtUtil;
import com.trier.trier_report.exception.AccessTokenExpiredException;
import com.trier.trier_report.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh-token",
            "/api/weather/today"
    );

    private final CustomUserDetailsService customUserDetailsService;
    private final HandlerExceptionResolver resolver;

    public JwtAuthenticationFilter(CustomUserDetailsService customUserDetailsService, HandlerExceptionResolver resolver) {
        this.customUserDetailsService = customUserDetailsService;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (EXCLUDED_PATHS.contains(path)) {
            filterChain.doFilter(request, response); // skip filter
            return;
        }

        String accessToken = JwtUtil.getAccessTokenFromRequest(request);

        try {
            if (accessToken != null && JwtUtil.validateAccessToken(accessToken) && CsrfTokenUtil.validateCsrfToken(request)) {
                String email = JwtUtil.getEmailFromAccessToken(accessToken);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                filterChain.doFilter(request, response);
            } else {
                throw new AccessTokenExpiredException("Invalid credentials");
            }
        } catch (AccessTokenExpiredException ex) {
            resolver.resolveException(request, response, null, ex);
        }
    }
}

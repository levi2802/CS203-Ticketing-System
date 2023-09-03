package com.cs203.TicketWarrior.Registration.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// This class acts as a filter by checking if the person visiting the browser has a valid token
// The jwt token
// It will then
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        //Get the header that contains the JWT token from the user html request
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        //Header should always start with bearer, if not, deny access and pass to next filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //Extract token from header
        token = authHeader.substring(7);

        //Get username by extracting it from token by using JWTService class
        username = jwtService.extractUsername(token);
    }
}

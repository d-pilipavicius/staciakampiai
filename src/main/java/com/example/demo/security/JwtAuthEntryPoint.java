package com.example.demo.security;


import com.example.demo.security.filters.CustomJwtExceptionFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        CustomJwtExceptionFilter.setErrorResponse(
                HttpServletResponse.SC_UNAUTHORIZED,
                response,
                "No bearer token has been provided inside the Authorization header or it has been formatted incorrectly.");
    }
}

package com.example.demo.security.ExceptionHandling;

import com.example.demo.security.filters.CustomJwtExceptionFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        CustomJwtExceptionFilter.setErrorResponse(
                HttpServletResponse.SC_FORBIDDEN,
                response,
                "You do not have permissions to access this resource");
    }
}

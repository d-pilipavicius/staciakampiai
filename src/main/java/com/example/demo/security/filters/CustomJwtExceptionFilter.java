package com.example.demo.security.filters;

import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.JwtExpiredException;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.JwtInvalidSigException;
import com.example.demo.CommonHelper.ErrorHandling.CustomExceptions.JwtMalformedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CustomJwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtExpiredException ex) {
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, ex.getErrorMsg());
        } catch (JwtInvalidSigException ex) {
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, ex.getErrorMsg());
        } catch (JwtMalformedException ex) {
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, ex.getErrorMsg());
        } catch (Exception ex){
            setErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, response, ex.getMessage());
        }
    }

    public static void setErrorResponse(int status, HttpServletResponse response, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(
                String.format("{\"status\": %d, \"message\": \"%s\"}", status, message)
        );
    }
}

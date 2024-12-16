package com.example.demo.security.interceptors;

import com.example.demo.security.filters.CustomJwtExceptionFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

@Component
public class BusinessAuthorizationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (pathVariables != null && pathVariables.get("businessId") != null) {
            String businessId = pathVariables.get("businessId");
            boolean hasAuthority = SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities()
                    .stream()
                    .anyMatch(auth -> auth.getAuthority().equals("BUSINESS_" + businessId)
                                || auth.getAuthority().equals("ITAdministrator"));

            if (!hasAuthority) {
                CustomJwtExceptionFilter.setErrorResponse(
                        HttpServletResponse.SC_FORBIDDEN,
                        response,
                        "The user does not belong to this business.");
                return false;
            }
        }

        return true;
    }
}

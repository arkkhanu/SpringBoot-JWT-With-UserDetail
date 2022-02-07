package com.ark.JWT.With.UserDetails.security;

import com.ark.JWT.With.UserDetails.domain.core_model.ExceptionResponse;
import com.ark.JWT.With.UserDetails.security.jwt_algorith.JwtGeneration;
import com.ark.JWT.With.UserDetails.utils.StringConstant;
import com.ark.JWT.With.UserDetails.utils.URLConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

//@ComponentScan({"com.ark.JWT.With.UserDetails.security.jwt_algorith.JwtGeneration"})
public class CustomAuthorizationFilter extends OncePerRequestFilter {
//    @Autowired
    private JwtGeneration jwtGeneration;

    public CustomAuthorizationFilter(JwtGeneration jwtGeneration) {
        this.jwtGeneration = jwtGeneration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//         UnBlocked URLs
        if (request.getServletPath().startsWith(URLConstant.URL_BaseTOKEN) ||
                request.getServletPath().equals(URLConstant.URL_User + "/register")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(StringConstant.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(StringConstant.BEARER)) {
                try {
                    String token = authorizationHeader.substring(StringConstant.BEARER.length());
                    String userName = jwtGeneration.getUserNameFromJWT(token);
                    Collection<SimpleGrantedAuthority> authorities = jwtGeneration.getUserRolesFromJWT(token);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    response.setHeader("Error", exception.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
//                    Map<String, String> errorMap = new HashMap<>();
//                    errorMap.put("Error_Message", exception.getMessage());
//                    response.setContentType(StringConstant.APPLICATION_JSON_VALUE);
//                    new ObjectMapper().writeValue(response.getOutputStream(), errorMap);
                    response.setContentType(StringConstant.APPLICATION_JSON_VALUE);
                    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), String.valueOf(HttpStatus.FORBIDDEN.value()), exception.getMessage());
                    new ObjectMapper().writeValue(response.getOutputStream(), exceptionResponse);
                    filterChain.doFilter(request, response);
                }

            } else {
                response.setHeader("Error", "Authorization Token Missing");
                response.setStatus(HttpStatus.FORBIDDEN.value());
//                Map<String, String> errorMap = new HashMap<>();
//                errorMap.put("Error_Message", "Authorization Token is Missing From Header");
                response.setContentType(StringConstant.APPLICATION_JSON_VALUE);
                ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), String.valueOf(HttpStatus.FORBIDDEN.value()), "Authorization Token is Missing From Header");
                new ObjectMapper().writeValue(response.getOutputStream(), exceptionResponse);
                filterChain.doFilter(request, response);
            }
        }
    }
}

package com.ark.JWT.With.UserDetails.security;

import com.ark.JWT.With.UserDetails.api.APIEndPoints;
import com.ark.JWT.With.UserDetails.domain.core_model.ExceptionResponse;
import com.ark.JWT.With.UserDetails.security.jwt_algorith.JwtGeneration;
import com.ark.JWT.With.UserDetails.utils.StringConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private JwtGeneration jwtGeneration;

    public CustomAuthorizationFilter(JwtGeneration jwtGeneration) {
        this.jwtGeneration = jwtGeneration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//         UnBlocked URLs

//        if (request.getServletPath().equals(APIEndPoints.URL_BASE_USER + APIEndPoints.URL_REGISTER_USER)) {
//            filterChain.doFilter(request, response);
//        }


        if (request.getServletPath().equals(APIEndPoints.URL_BASE_USER + APIEndPoints.URL_REGISTER_USER) ||
                request.getServletPath().equals(APIEndPoints.URL_BASE_USER + APIEndPoints.URL_AUTHENTICATE_USER)
        ) {
            filterChain.doFilter(request, response);
        }
        else {
            String authorizationHeader = request.getHeader(StringConstant.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(StringConstant.BEARER)) {
                try {
                    String token = authorizationHeader.substring(StringConstant.BEARER.length());
                    String userName = jwtGeneration.getUserNameFromJWT(token);
                    Collection<SimpleGrantedAuthority> authorities = jwtGeneration.getUserRolesFromJWT(token);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }
                catch (Exception exception) {
                    response.setHeader("Error", exception.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(StringConstant.APPLICATION_JSON_VALUE);
                    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), String.valueOf(HttpStatus.FORBIDDEN.value()), exception.getMessage());
                    new ObjectMapper().writeValue(response.getOutputStream(), exceptionResponse);
                    filterChain.doFilter(request, response);
                }

            }
            else {
                response.setHeader("Error", "Authorization Token Missing");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.setContentType(StringConstant.APPLICATION_JSON_VALUE);
                ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), String.valueOf(HttpStatus.FORBIDDEN.value()), "Authorization Token is Missing From Header");
                new ObjectMapper().writeValue(response.getOutputStream(), exceptionResponse);
                filterChain.doFilter(request, response);
            }
        }
    }
}

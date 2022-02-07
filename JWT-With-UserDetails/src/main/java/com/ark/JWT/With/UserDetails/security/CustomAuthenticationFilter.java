package com.ark.JWT.With.UserDetails.security;

import com.ark.JWT.With.UserDetails.domain.User;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        return super.attemptAuthentication(request, response);
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userName, password);
        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);
        User user = (User) authResult.getPrincipal();
//        Algorithm algorithm =Algorithm
    }
}

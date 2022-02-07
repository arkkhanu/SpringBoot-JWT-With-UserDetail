package com.ark.JWT.With.UserDetails.api;

import com.ark.JWT.With.UserDetails.domain.User;
import com.ark.JWT.With.UserDetails.domain.core_model.BaseRespose;
import com.ark.JWT.With.UserDetails.domain.core_model.JWTRequest;
import com.ark.JWT.With.UserDetails.security.jwt_algorith.JwtGeneration;
import com.ark.JWT.With.UserDetails.services.UserService;
import com.ark.JWT.With.UserDetails.utils.URLConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping(URLConstant.URL_BaseTOKEN)
public class LoginAPI {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;
    @Autowired
    JwtGeneration jwtGeneration;

    @PostMapping(URLConstant.URL_Login)
    private ResponseEntity<?> authenticateUser(@RequestBody JWTRequest jwtRequest, HttpServletRequest request) throws Exception {

        request.getRequestURL();

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        User user = (User) userService.loadUserByUsername(jwtRequest.getUserName());
        String accessToken = jwtGeneration.tokenGenerate(user, request);
        String refreshToken = jwtGeneration.refreshToken(user, request);

        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), accessToken, null, user), HttpStatus.OK);
    }
}

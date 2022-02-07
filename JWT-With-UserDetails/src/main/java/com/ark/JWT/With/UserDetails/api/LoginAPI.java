package com.ark.JWT.With.UserDetails.api;

import com.ark.JWT.With.UserDetails.domain.User;
import com.ark.JWT.With.UserDetails.domain.core_model.BaseRespose;
import com.ark.JWT.With.UserDetails.domain.core_model.JWTRequest;
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

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(URLConstant.URL_BaseTOKEN)
public class LoginAPI {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    @PostMapping(URLConstant.URL_Login)
    private ResponseEntity<?> authenticateUser(@RequestBody JWTRequest jwtRequest) throws Exception {

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
//        UserDetails user = userService.loadUserByUsername(jwtRequest.getUserName());
        User user = (User) userService.loadUserByUsername(jwtRequest.getUserName());
        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), user), HttpStatus.OK);
    }
}

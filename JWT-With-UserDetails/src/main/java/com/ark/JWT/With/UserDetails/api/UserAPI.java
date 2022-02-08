package com.ark.JWT.With.UserDetails.api;

import com.ark.JWT.With.UserDetails.domain.core_model.BaseRespose;
import com.ark.JWT.With.UserDetails.domain.User;
import com.ark.JWT.With.UserDetails.domain.core_model.JWTRequest;
import com.ark.JWT.With.UserDetails.security.jwt_algorith.JwtGeneration;
import com.ark.JWT.With.UserDetails.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(APIEndPoints.URL_BASE_USER)
public class UserAPI {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtGeneration jwtGeneration;

    @PostMapping(APIEndPoints.URL_AUTHENTICATE_USER)
    private ResponseEntity<?> authenticateUser(@RequestBody JWTRequest jwtRequest, HttpServletRequest request) throws Exception {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        User user = (User) userService.loadUserByUsername(jwtRequest.getUserName());
        String accessToken = jwtGeneration.tokenGenerate(user, request);
        String refreshToken = jwtGeneration.refreshToken(user, request);
        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), accessToken, null, user), HttpStatus.OK);
    }

    @PostMapping(APIEndPoints.URL_REGISTER_USER)
    private ResponseEntity<BaseRespose<User>> saveUser(@Valid @RequestBody User user) throws Exception {
        User _u = userService.saveUser(user);
        return ResponseEntity.ok(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), null, null, _u));
    }

    @GetMapping("/getAllUsers")
    private ResponseEntity<?> getAllUsers(HttpServletResponse response) {
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), null, null, userList), HttpStatus.OK);
    }


    @GetMapping("/")
    private ResponseEntity<?> getUserByUserName(@PathParam("userName") String userName) throws Exception {
        User user = userService.getUserByUserName(userName);
        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), null, null, user), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    private ResponseEntity<?> getUserByUserId(@PathVariable String userId) throws Exception {
        User user = userService.getUserByUserId(userId);
        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), null, null, user), HttpStatus.OK);
    }

//    @GetMapping("/{userId}")
//    private ResponseEntity<?> getUserByUserId( @PathVariable String userId) throws Exception {
//        User user = userService.getUserByUserId(userId);
//        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), null, null, user), HttpStatus.OK);
//    }


}

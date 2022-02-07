package com.ark.JWT.With.UserDetails.api;

import com.ark.JWT.With.UserDetails.domain.BaseRespose;
import com.ark.JWT.With.UserDetails.domain.User;
import com.ark.JWT.With.UserDetails.exception.inner_exception.UserAlreadyFoundException;
import com.ark.JWT.With.UserDetails.exception.inner_exception.UserNotFoundException;
import com.ark.JWT.With.UserDetails.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("/user")
public class UserAPI {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/save")
    private ResponseEntity<BaseRespose<User>> saveUser(@Valid @RequestBody User user) throws Exception {
        User _u = userService.saveUser(user);
        return ResponseEntity.ok(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), _u));
    }

    @GetMapping("/getAllUsers")
    private ResponseEntity<?> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()), userList), HttpStatus.OK);
    }
}

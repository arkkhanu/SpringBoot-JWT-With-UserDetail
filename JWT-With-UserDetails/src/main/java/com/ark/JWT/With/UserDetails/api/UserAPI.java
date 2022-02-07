package com.ark.JWT.With.UserDetails.api;

import com.ark.JWT.With.UserDetails.domain.core_model.BaseRespose;
import com.ark.JWT.With.UserDetails.domain.User;
import com.ark.JWT.With.UserDetails.services.UserService;
import com.ark.JWT.With.UserDetails.utils.URLConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(URLConstant.URL_User)
public class UserAPI {

    @Autowired
    private UserService userService;


    @PostMapping(value = "/register")
    private ResponseEntity<BaseRespose<User>> saveUser(@Valid @RequestBody User user) throws Exception {
        User _u = userService.saveUser(user);
        return ResponseEntity.ok(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()),null,null, _u));
    }

    @GetMapping("/getAllUsers")
    private ResponseEntity<?> getAllUsers() {
        List<User> userList = userService.getAllUsers();

        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()),null,null, userList), HttpStatus.OK);
    }
    @GetMapping("/getAll")
    private ResponseEntity<?> getAll() {
        List<User> userList = userService.getAllUsers();

        return new ResponseEntity<>(new BaseRespose<>(new Date(), null, String.valueOf(HttpStatus.OK.value()),null,null, userList), HttpStatus.OK);
    }
}

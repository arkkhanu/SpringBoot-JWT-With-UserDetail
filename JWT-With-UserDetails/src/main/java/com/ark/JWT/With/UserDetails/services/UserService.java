package com.ark.JWT.With.UserDetails.services;

import com.ark.JWT.With.UserDetails.domain.User;
import com.ark.JWT.With.UserDetails.exception.inner_exception.UserAlreadyFoundException;
import com.ark.JWT.With.UserDetails.exception.inner_exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService{

    User saveUser(User user) throws Exception;
    User getUserByUserName(String  userName) throws Exception;
    User getUserByUserId(String  userId) throws Exception;
    List<User> getAllUsers();

}

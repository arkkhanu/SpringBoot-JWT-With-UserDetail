package com.ark.JWT.With.UserDetails.services.impl;

import com.ark.JWT.With.UserDetails.domain.Role;
import com.ark.JWT.With.UserDetails.domain.User;
import com.ark.JWT.With.UserDetails.domain.UserRole;
import com.ark.JWT.With.UserDetails.exception.inner_exception.UserAlreadyFoundException;
import com.ark.JWT.With.UserDetails.exception.inner_exception.UserNotFoundException;
import com.ark.JWT.With.UserDetails.repository.RoleRepository;
import com.ark.JWT.With.UserDetails.repository.UserRepository;
import com.ark.JWT.With.UserDetails.services.UserService;
import com.ark.JWT.With.UserDetails.utils.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User saveUser(User user) throws Exception {
        User _ifFoundUser = userRepository.findByUserName(user.getUserName());
        Role _ifFoundRole = roleRepository.findByRoleName(StringConstant.NORMAL_ROLE);
        if (_ifFoundUser != null) {
            throw new UserAlreadyFoundException("User Already Found, Please try different Username");
        }
        if (_ifFoundRole == null) {
            _ifFoundRole = roleRepository.save(new Role(StringConstant.NORMAL_ROLE));
        }
        user.getUserRoles().add(new UserRole(user, _ifFoundRole));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User _ifFoundUser = userRepository.findByUserName(username);
        if (_ifFoundUser == null) {
            throw new UsernameNotFoundException("User Name Not Found");
        }
        return _ifFoundUser;
    }
}

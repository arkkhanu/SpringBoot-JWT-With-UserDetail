package com.ark.JWT.With.UserDetails.exception.inner_exception;

public class UserNotFoundException extends Exception {
    private String message;

    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}

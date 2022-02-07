package com.ark.JWT.With.UserDetails.exception.inner_exception;


public class UserAlreadyFoundException extends Exception {
    private String message;

    public UserAlreadyFoundException(String message) {
        super(message);
        this.message = message;
    }
}

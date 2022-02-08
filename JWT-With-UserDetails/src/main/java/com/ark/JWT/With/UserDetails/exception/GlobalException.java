package com.ark.JWT.With.UserDetails.exception;

import com.ark.JWT.With.UserDetails.domain.core_model.ExceptionResponse;
import com.ark.JWT.With.UserDetails.exception.inner_exception.UserAlreadyFoundException;
import com.ark.JWT.With.UserDetails.exception.inner_exception.UserNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalException {

//    @ExceptionHandler(value = {UsernameNotFoundException.class})
//    private ResponseEntity<?> handleUserFoundException(UserNotFoundException userNotFoundException, WebRequest webRequest) {
//        return new ResponseEntity<>(new ExceptionResponse(new Date(), String.valueOf(HttpStatus.FORBIDDEN.value()), userNotFoundException.getMessage()), HttpStatus.FORBIDDEN);
//    }

    @ExceptionHandler(value = {AuthenticationException.class})
    private ResponseEntity<?> handlAuthException(AuthenticationException authenticationException, WebRequest webRequest) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(), String.valueOf(HttpStatus.FORBIDDEN.value()), authenticationException.getMessage()), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(value = {UserNotFoundException.class})
    private ResponseEntity<?> handleUserNotFoundException(UserNotFoundException userNotFoundException, WebRequest webRequest) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(), String.valueOf(HttpStatus.FORBIDDEN.value()), userNotFoundException.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UserAlreadyFoundException.class})
    private ResponseEntity<?> handleUserAlreadyFoundException(UserAlreadyFoundException userAlreadyFoundException, WebRequest webRequest) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(), String.valueOf(HttpStatus.NOT_FOUND.value()), userAlreadyFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    private ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {
        String message = methodArgumentNotValidException.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        return new ResponseEntity<>(new ExceptionResponse(new Date(), String.valueOf(HttpStatus.NOT_FOUND.value()), message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    private ResponseEntity<?> handleRuntimeException(RuntimeException runtimeException, WebRequest webRequest) {
        String message = runtimeException.getMessage().substring(0, runtimeException.getMessage().indexOf(":"));
        return new ResponseEntity<>(new ExceptionResponse(new Date(), String.valueOf(HttpStatus.NOT_FOUND.value()), message), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {NumberFormatException.class})
    private ResponseEntity<?> handleNumberFormatException(NumberFormatException numberFormatException, WebRequest webRequest) {
        String message = numberFormatException.getMessage().substring(0, numberFormatException.getMessage().indexOf(":"));
        return new ResponseEntity<>(new ExceptionResponse(new Date(), String.valueOf(HttpStatus.NOT_FOUND.value()), message), HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(value = {AuthenticationException.class})
//    private ResponseEntity<?> handleUnauthorized(AuthenticationException authenticationException, WebRequest webRequest) {
//        String message = authenticationException.getMessage().substring(0, authenticationException.getMessage().indexOf(":"));
//        return new ResponseEntity<>(new ExceptionResponse(new Date(), String.valueOf(HttpStatus.NOT_FOUND.value()), message), HttpStatus.BAD_REQUEST);
//    }

}

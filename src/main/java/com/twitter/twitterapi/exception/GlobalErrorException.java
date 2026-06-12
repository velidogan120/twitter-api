package com.twitter.twitterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorException {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleException(ApiException e) {
        ApiResponse apiResponse = new ApiResponse(e.getMessage(),e.getHttpStatus().value(),System.currentTimeMillis());
        return new ResponseEntity<>(apiResponse,e.getHttpStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleException(UsernameNotFoundException e) {
        ApiResponse apiResponse = new ApiResponse(e.getMessage(),HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleException(BadCredentialsException e) {
        ApiResponse apiResponse = new ApiResponse(e.getMessage(),HttpStatus.UNAUTHORIZED.value(), System.currentTimeMillis());
        return new ResponseEntity<>(apiResponse,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        ApiResponse apiResponse = new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),System.currentTimeMillis());
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

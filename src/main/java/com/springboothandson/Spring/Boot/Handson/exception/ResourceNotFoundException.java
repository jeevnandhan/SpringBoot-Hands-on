package com.springboothandson.Spring.Boot.Handson.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

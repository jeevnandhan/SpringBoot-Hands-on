package com.springboothandson.Spring.Boot.Handson.exception;



public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

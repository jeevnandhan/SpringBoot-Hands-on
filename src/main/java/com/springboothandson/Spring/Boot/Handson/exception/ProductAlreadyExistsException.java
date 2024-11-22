package com.springboothandson.Spring.Boot.Handson.exception;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String s)  {
        super(s);
    }
}

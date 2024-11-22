package com.springboothandson.Spring.Boot.Handson.controller;

import com.springboothandson.Spring.Boot.Handson.model.CartProduct;
import com.springboothandson.Spring.Boot.Handson.model.Product;
import com.springboothandson.Spring.Boot.Handson.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/consumer")
@RequiredArgsConstructor
public class ConsumerController {

    private final ConsumerService consumerService;

    @GetMapping("/cart")
    public ResponseEntity<?> getCart(){
        return consumerService.getCart(getCurrentUser());
    }

    @PostMapping("/cart")
    public ResponseEntity<?> postCart(@RequestBody Product productRequest){
        return consumerService.addProductToCart(getCurrentUser(), productRequest);
    }

    @PutMapping("/cart")
    public ResponseEntity<?> putCart(@RequestBody CartProduct cartProductRequest){
        return consumerService.updateCart(getCurrentUser(), cartProductRequest);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> removeCartProduct(@RequestBody Product productRequest){
        return consumerService.removeProductFromCart(getCurrentUser(), productRequest);
    }

    private String getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }
}

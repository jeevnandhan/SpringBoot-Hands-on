package com.springboothandson.Spring.Boot.Handson.controller;

import com.springboothandson.Spring.Boot.Handson.model.Product;
import com.springboothandson.Spring.Boot.Handson.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("api/auth/seller")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/product")
    public ResponseEntity<?> getAllProducts(){
        return sellerService.getAllProducts(getCurrentUser());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Integer productId){
        return sellerService.getProduct(getCurrentUser(), productId);
    }

    @PostMapping("/product")
    public ResponseEntity<?> postProduct(@RequestBody Product productRequest){
        return sellerService.saveProduct(getCurrentUser(), productRequest);
    }

    @PutMapping("/product")
    public ResponseEntity<?> putProduct(@Valid @RequestBody Product productRequest, BindingResult bindingResult){


        if(bindingResult.hasErrors()){
            String defaultMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.badRequest().body(defaultMessage);

        }
        return sellerService.updateProduct(getCurrentUser(), productRequest);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        return sellerService.deleteProduct(getCurrentUser(), productId);
    }

    private String getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getPrincipal();
    }

}

package com.springboothandson.Spring.Boot.Handson.controller;

import com.springboothandson.Spring.Boot.Handson.dto.ProductDto;
import com.springboothandson.Spring.Boot.Handson.Request.AuthRequest;
import com.springboothandson.Spring.Boot.Handson.service.AuthService;
import com.springboothandson.Spring.Boot.Handson.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final ProductService productService;
    private final AuthService authService;

    @GetMapping("/product/search")
    public ResponseEntity<List<ProductDto>> getProducts(@RequestParam String keyword){

        try {
            List<ProductDto> productsByKeyword = productService.getProductsByKeyword(keyword);
            return ResponseEntity.ok(productsByKeyword);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest userRequest){
        return authService.login(userRequest);
    }
}

package com.springboothandson.Spring.Boot.Handson.service;

import com.springboothandson.Spring.Boot.Handson.exception.ResourceNotFoundException;
import com.springboothandson.Spring.Boot.Handson.model.Product;
import com.springboothandson.Spring.Boot.Handson.model.User;
import com.springboothandson.Spring.Boot.Handson.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final ProductService productService;

    public ResponseEntity<?> getAllProducts(String username) {

        try{
            List<Product> productsBySeller = productService.getProductsBySeller(username);
            return ResponseEntity.status(HttpStatus.OK).body(productsBySeller);

        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<?> getProduct(String username, Integer productId) {
        try{
            Product product = productService.getProductById(username, productId);
            return ResponseEntity.status(HttpStatus.OK).body(product);

        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity<?> saveProduct(String username, Product productRequest) {
        try{
            Product savedProduct = productService.saveProduct(username, productRequest);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedProduct.getProductId())
                    .toUri();
//            return ResponseEntity.status(HttpStatus.CREATED)
//                    .header("Location", "/api/auth/seller/product/" + savedProduct.getProductId())
//                    .body(savedProduct);

            return ResponseEntity.created(location).body(savedProduct);

        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    public ResponseEntity<?> updateProduct(String username, Product productRequest) {

        try{
            productService.updateProduct(username, productRequest);
            return ResponseEntity.status(HttpStatus.OK).body(null);

        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    public ResponseEntity<?> deleteProduct(String username, Integer productId) {

        try{
            if(productService.deleteProduct(username, productId)){
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

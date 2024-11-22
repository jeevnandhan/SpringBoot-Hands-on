package com.springboothandson.Spring.Boot.Handson.service;

import com.springboothandson.Spring.Boot.Handson.exception.ProductAlreadyExistsException;
import com.springboothandson.Spring.Boot.Handson.exception.ResourceNotFoundException;
import com.springboothandson.Spring.Boot.Handson.model.*;
import com.springboothandson.Spring.Boot.Handson.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public Cart getCartForUser(String username){

        return cartRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }
    public void addProductToCart(String username, Product productRequest){

           productRepository.findById(productRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exists"));

            Cart cart = getCartForUser(username);

            Optional<CartProduct> existingCartProduct = cart.getCartProducts()
                    .stream()
                    .filter(cp -> cp.getProduct().getProductId().equals(productRequest.getProductId()))
                    .findFirst();
            if (existingCartProduct.isPresent()) {
                throw new ProductAlreadyExistsException("Product already in cart");
            }

            CartProduct newCartProduct = new CartProduct();
            newCartProduct.setProduct(productRequest);
            newCartProduct.setCart(cart);
            newCartProduct.setQuantity(1); // default quantity


            cart.getCartProducts().add(newCartProduct);
            cart.updateTotalAmount(newCartProduct.getProduct().getPrice()* newCartProduct.getQuantity());

            cartProductRepository.save(newCartProduct);
            cartRepository.save(cart);
    }

    @Transactional
    public void updateCartProduct(String username, CartProduct cartProductRequest) {

        Cart cart = getCartForUser(username);

        Optional<CartProduct> existingCartProduct = cart.getCartProducts()
                .stream()
                .filter(cp -> cp.getProduct().getProductId().equals(cartProductRequest.getProduct().getProductId()))
                .findFirst();

            if(cartProductRequest.getQuantity() == 0){
                existingCartProduct.ifPresent(cp -> {
                            cart.getCartProducts().remove(cp);
                            cart.setTotalAmount(cart.getTotalAmount() - cp.getProduct().getPrice()*cp.getQuantity());
                            cartProductRepository.deleteById(cp.getCpId());
                });
            } else {

                existingCartProduct.ifPresentOrElse(cp -> {
                    cart.setTotalAmount(cart.getTotalAmount() - cp.getProduct().getPrice() * cp.getQuantity());
                    cp.setQuantity(cartProductRequest.getQuantity());
                    cart.setTotalAmount(cart.getTotalAmount() + cp.getProduct().getPrice() * cp.getQuantity());
                }, () -> {
                    CartProduct newCartProduct = new CartProduct();
                    newCartProduct.setCart(cart);
                    newCartProduct.setProduct(cartProductRequest.getProduct());
                    newCartProduct.setQuantity(cartProductRequest.getQuantity());

                    cart.getCartProducts().add(newCartProduct);
                    cart.updateTotalAmount(newCartProduct.getProduct().getPrice() * newCartProduct.getQuantity());
                    cartProductRepository.save(newCartProduct);
                });
            }
            cartRepository.save(cart);

    }

    @Transactional
    public void removeProductFromCart(String username, Product productRequest) {

        Cart cart = getCartForUser(username);
        CartProduct cartProductToBeRemoved = cart.getCartProducts()
                .stream()
                .filter(cp -> cp.getProduct().getProductId().equals(productRequest.getProductId()))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        cart.getCartProducts().remove(cartProductToBeRemoved);
        double newTotal = cart.getTotalAmount() - productRequest.getPrice() * cartProductToBeRemoved.getQuantity();
        cart.setTotalAmount(newTotal);
        cartProductRepository.delete(cartProductToBeRemoved);
        cartRepository.save(cart);

    }


}

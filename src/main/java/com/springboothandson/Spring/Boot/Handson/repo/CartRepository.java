package com.springboothandson.Spring.Boot.Handson.repo;

import com.springboothandson.Spring.Boot.Handson.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUserUsername(String username);

}

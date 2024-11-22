package com.springboothandson.Spring.Boot.Handson.repo;

import com.springboothandson.Spring.Boot.Handson.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {
    Optional<CartProduct> findByCartUserUserIdAndProductProductId(Integer userId, Integer productId);

    @Transactional
    void deleteByCartUserUserIdAndProductProductId(Integer userId, Integer productId);
}

package com.springboothandson.Spring.Boot.Handson.repo;

import com.springboothandson.Spring.Boot.Handson.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {

}

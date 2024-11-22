package com.springboothandson.Spring.Boot.Handson.repo;

import com.springboothandson.Spring.Boot.Handson.model.Product;
import com.springboothandson.Spring.Boot.Handson.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query(value = "SELECT p FROM Product p WHERE LOWER(p.productName) LIKE %:keyword% OR LOWER(p.category.categoryName) LIKE %:keyword%")
    List<Product> getProductsByKeyword(@Param("keyword") String keyword);

    List<Product> findBySeller(User seller);

    Optional<Product> findBySellerUserIdAndProductId(Integer userId, Integer productId);

    //List<Product>findByProductNameOrCategoryCategoryName(String productName, String categoryName);
//    List<Product> findByProductProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(String productName, String categoryName);
//    List<Product> findBySellerUserId(Integer sellerId);
//
//    Optional<Product> findBySellerUserIdAndProductProductId(Integer userId, Product productId);

}

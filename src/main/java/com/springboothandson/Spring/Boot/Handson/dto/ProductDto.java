package com.springboothandson.Spring.Boot.Handson.dto;


import com.springboothandson.Spring.Boot.Handson.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProductDto {

    private Integer productId;
    private String productName;
    private Category category;

}

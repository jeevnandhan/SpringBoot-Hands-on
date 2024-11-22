package com.springboothandson.Spring.Boot.Handson.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    private Double totalAmount;

    @OneToOne
    @JsonIgnore
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartProduct> cartProducts;

    public void updateTotalAmount(Double price){
        this.totalAmount += price;
    }

}

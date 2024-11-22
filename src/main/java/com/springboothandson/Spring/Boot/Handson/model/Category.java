package com.springboothandson.Spring.Boot.Handson.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "categoryName")})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer categoryId;

    @Column(unique = true, length = 11)
    private String categoryName;
}

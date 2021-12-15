package com.blck_rbbit.gbspringlessonschapter1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "cost", nullable = false)
    private Integer cost;
    
    public Product(String title, Integer cost) {
        this.title = title;
        this.cost = cost;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Product {id: %s, title: %s, cost: %s%n}", id, title, cost
        );
    }
    
}

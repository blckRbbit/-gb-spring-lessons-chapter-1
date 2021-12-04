package com.blck_rbbit.gbspringlessonschapter1.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "cost", nullable = false)
    private Integer cost;
    
    
    public Product() {
    }
    
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
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Integer getCost() {
        return cost;
    }
    
    public void setCost(Integer cost) {
        this.cost = cost;
    }
    
}

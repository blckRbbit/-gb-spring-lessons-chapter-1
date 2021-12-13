package com.blck_rbbit.gbspringlessonschapter1.dto;

import com.blck_rbbit.gbspringlessonschapter1.entities.Product;

public class ProductDTO {
    private Long id;
    private String title;
    private Integer cost;
    
    public ProductDTO() {
    }
    
    public ProductDTO(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.cost = product.getCost();
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

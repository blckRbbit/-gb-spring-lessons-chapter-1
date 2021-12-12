package com.blck_rbbit.gbspringlessonschapter1.dto;

import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductDTO {
    private Long id;
    @NotNull(message = "Enter product name")
    @Length(min = 3, max = 255, message = "Product name must be between 3 and 255 characters long")
    private String title;
    
    @Min(value = 1, message = "The price of the goods must be at least 1 ruble")
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

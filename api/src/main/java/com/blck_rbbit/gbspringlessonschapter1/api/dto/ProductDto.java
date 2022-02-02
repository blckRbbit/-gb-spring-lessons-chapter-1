package com.blck_rbbit.gbspringlessonschapter1.api.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private Integer cost;
    private CategoryDto categoryDto;
    
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
    
    public CategoryDto getCategoryDto() {
        return categoryDto;
    }
    
    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }
    
}

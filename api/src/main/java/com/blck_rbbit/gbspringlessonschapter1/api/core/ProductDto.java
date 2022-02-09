package com.blck_rbbit.gbspringlessonschapter1.api.core;

public class ProductDto {
    private Long id;
    private String title;
    private Integer cost;
    private CategoryDto categoryDto;

    
    public ProductDto(Long id, String title, Integer cost, CategoryDto categoryDto) {
        this.id = id;
        this.title = title;
        this.cost = cost;
        this.categoryDto = categoryDto;
    }
    
    public ProductDto() {
    
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
    
    public CategoryDto getCategoryDto() {
        return categoryDto;
    }
    
    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }
}
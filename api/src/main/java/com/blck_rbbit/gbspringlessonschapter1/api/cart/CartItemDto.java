package com.blck_rbbit.gbspringlessonschapter1.api.cart;

import java.time.LocalDateTime;

public class CartItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private int pricePerProduct;
    private int price;
    private LocalDateTime createdAt;
    private int additionPerDay;
    
    public CartItemDto() {
        this.createdAt = LocalDateTime.now();
        this.additionPerDay = 0;
    }
    
    public CartItemDto(Long productId, String productTitle, int pricePerProduct) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.pricePerProduct = pricePerProduct;
        this.createdAt = LocalDateTime.now();
        this.additionPerDay = 0;
    }
    
    public CartItemDto(Long productId, String productTitle, int quantity, int pricePerProduct, int price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.pricePerProduct = pricePerProduct;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.additionPerDay = 0;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public String getProductTitle() {
        return productTitle;
    }
    
    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public int getPricePerProduct() {
        return pricePerProduct;
    }
    
    public void setPricePerProduct(int pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public int getAdditionPerDay() {
        return additionPerDay;
    }
    
    public void setAdditionPerDay(int additionPerDay) {
        this.additionPerDay = additionPerDay;
    }
}

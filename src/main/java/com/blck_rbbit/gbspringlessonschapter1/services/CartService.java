package com.blck_rbbit.gbspringlessonschapter1.services;

import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import com.blck_rbbit.gbspringlessonschapter1.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.persistens.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart cart;
    
    @PostConstruct
    public void init() {
        cart = new Cart();
    }
    
    public Cart getCurrentCart() {return cart;}
    
    public void addProductByIdToCart(Long productId) {
        if (!getCurrentCart().addProduct(productId)) {
            String message = "Unable to add product to cart. Product with id = " + productId + " not found";
            Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException(message));
            getCurrentCart().addProduct(product);
        }
    }
    
    public void clear() {
        getCurrentCart().clear();
    }
}

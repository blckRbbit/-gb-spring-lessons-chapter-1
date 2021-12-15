package com.blck_rbbit.gbspringlessonschapter1.controllers;

import com.blck_rbbit.gbspringlessonschapter1.converters.ProductConverter;
import com.blck_rbbit.gbspringlessonschapter1.dto.ProductDTO;
import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import com.blck_rbbit.gbspringlessonschapter1.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.handlers.CartHandler;
import com.blck_rbbit.gbspringlessonschapter1.persistens.Cart;
import com.blck_rbbit.gbspringlessonschapter1.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final Cart cart;
    private final CartHandler cartHandler;
    private final ProductConverter productConverter;
    private final ProductService productService;
    
    @GetMapping
    public List<ProductDTO> getCartProducts() {
        return cart.getPurchase();
    }
    
    @PostMapping
    public Cart addToCart(@RequestBody Long id) {
        Optional<Product> product = Optional
                .ofNullable(productService.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found")));
        product.ifPresent(cartHandler::addProductToCart);
        return cart;
    }
    
    @DeleteMapping
    public void clear() {
        cartHandler.clear();
    }
    
    @DeleteMapping("/{id}")
    public void deleteByIndex(@PathVariable Long id) {
        cartHandler.delete(id);
    }
    
}

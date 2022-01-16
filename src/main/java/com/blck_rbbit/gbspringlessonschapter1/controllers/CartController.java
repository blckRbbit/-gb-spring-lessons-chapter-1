package com.blck_rbbit.gbspringlessonschapter1.controllers;

import com.blck_rbbit.gbspringlessonschapter1.entities.Order;
import com.blck_rbbit.gbspringlessonschapter1.persistens.Cart;
import com.blck_rbbit.gbspringlessonschapter1.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    
    @GetMapping
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }
    
    @GetMapping("/add/{id}")
    public void addProductToCart(@PathVariable Long id) {
        cartService.addProductByIdToCart(id);
    }
    
    @GetMapping("/clear")
    public void clearCart() {
        cartService.getCurrentCart().clear();
    }
    
    @PostMapping
    public Order createOrder(@RequestParam Integer totalPrice, @RequestParam String address, @RequestParam String phone) {
        Order order = new Order(totalPrice, address, phone);
        return order;
    }
}

package com.blck_rbbit.gbspringlessonschapter1.services;

import com.blck_rbbit.gbspringlessonschapter1.api.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.cart.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productsService;
    private final RedisTemplate<String, Object> redisTemplate;
    
    @Value("${utils.cart.prefix}")
    private String cartPrefix;
    
    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }
    
    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }
    
    public Cart getCurrentCart(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }
    
    public void addToCart(String cartKey, Long productId) {
        RestTemplate restTemplate = new RestTemplate();
        List products = restTemplate.getForObject("http://localhost:8701/core/api/v1/products/", List.class);
        Product product = productsService.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + productId));
        execute(cartKey, c -> {
            c.add(product);
        });
    }
    
    public void clearCart(String cartKey) {
        execute(cartKey, Cart::clear);
    }
    
    public void removeItemFromCart(String cartKey, Long productId) {
        execute(cartKey, c -> c.remove(productId));
    }
    
    public void decrementItem(String cartKey, Long productId) {
        execute(cartKey, c -> c.decrement(productId));
    }
    
    public void merge(String userCartKey, String guestCartKey) {
        Cart guestCart = getCurrentCart(guestCartKey);
        Cart userCart = getCurrentCart(userCartKey);
        userCart.merge(guestCart);
        updateCart(guestCartKey, guestCart);
        updateCart(userCartKey, userCart);
    }
    
    private void execute(String cartKey, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }
    
    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}
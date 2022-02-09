package com.blck_rbbit.gbspringlessonschapter1.cartapp.services;

import com.blck_rbbit.gbspringlessonschapter1.api.cart.CartItemDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.api.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.integrations.CoreServiceIntegration;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final CoreServiceIntegration coreServiceIntegration;
    private final List<CartItemDto> TOP_CART_ITEMS_PER_DAY = new ArrayList<>();

    @Value("${utils.cart.prefix}")
    private String cartPrefix;

    public String getCartUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateCartUuid() {
        return UUID.randomUUID().toString();
    }

    public Cart getCurrentCart(String cartKey) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(cartKey))) {
            redisTemplate.opsForValue().set(cartKey, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(cartKey);
    }
    
    public void addToCart(String cartKey, Long productId) {
        getTime();
        ProductDto product = coreServiceIntegration.getProductById(productId);
        if (Objects.requireNonNull(product).getId().equals(productId)) {
            execute(cartKey, cart -> cart.add(product));
            fillTopCartItemPerDay(new CartItemDto(product.getId(), product.getTitle(), product.getCost()));
        } else {
            throw new ResourceNotFoundException("Unable to add product to cart. Product not found, id: " + productId);
        }
    }
    
    private void getTime() {
        if (LocalDateTime.now().equals(LocalDateTime.MIN)) {
            TOP_CART_ITEMS_PER_DAY.clear();
        }
    }
    
    public List<CartItemDto> getTopCartItemPerDay() {return TOP_CART_ITEMS_PER_DAY;}
    
    public void fillTopCartItemPerDay(CartItemDto cartItem) {
        if (dateIsToday(cartItem)) {
            TOP_CART_ITEMS_PER_DAY.add(cartItem);
            List<CartItemDto> temp = TOP_CART_ITEMS_PER_DAY.stream()
                    .filter(t -> t.getProductId().equals(cartItem.getProductId()))
                    .collect(Collectors.toList());
            cartItem.setAdditionPerDay(temp.size());
        }
    }
    
    private boolean dateIsToday(CartItemDto cartItem) {
        ChronoLocalDate today = LocalDateTime.now().toLocalDate();
        return !cartItem.getCreatedAt().toLocalDate().isAfter(today) && !cartItem.getCreatedAt().toLocalDate().isBefore(today);
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
//        updateCart(cartKey, cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateCart(String cartKey, Cart cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}

package com.blck_rbbit.gbspringlessonschapter1.cartapp.converters;

import com.blck_rbbit.gbspringlessonschapter1.api.cart.CartDto;
import com.blck_rbbit.gbspringlessonschapter1.api.cart.CartItemDto;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.models.Cart;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    public CartDto modelToDto(Cart cart) {
        List<CartItemDto> items = cart.getItems().stream().map(it ->
                new CartItemDto(it.getProductId(), it.getProductTitle(), it.getQuantity(), it.getPricePerProduct(), it.getPrice())
        ).collect(Collectors.toList());
        return new CartDto(items, cart.getTotalPrice());
    }
}
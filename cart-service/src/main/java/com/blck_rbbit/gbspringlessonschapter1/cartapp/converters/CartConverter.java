package com.blck_rbbit.gbspringlessonschapter1.cartapp.converters;

import com.blck_rbbit.gbspringlessonschapter1.api.dto.CartDto;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.persist.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {
    public Cart dtoToEntity(CartDto cartDto) {
        return new Cart(cartDto.getItems(), cartDto.getTotalPrice());
    }
    
    public CartDto entityToDTO(Cart cart) {
        return new CartDto(cart.getItems(), cart.getTotalPrice());
    }
}

package com.blck_rbbit.gbspringlessonschapter1.handlers;

import com.blck_rbbit.gbspringlessonschapter1.converters.ProductConverter;
import com.blck_rbbit.gbspringlessonschapter1.dto.ProductDTO;
import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import com.blck_rbbit.gbspringlessonschapter1.persistens.Cart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartHandler {
    private final Cart cart;
    private final ProductConverter productConverter;
    
    public void addProductToCart(Product product) {
        ProductDTO productDTO = productConverter.entityToDTO(product);
        cart.getPurchase().add(productDTO);
        cart.setPrice(productDTO.getCost());
        cart.setQuantity(cart.getPurchase().size());
        log.debug(String.valueOf(cart.getPurchase()));
        System.out.println(cart.getPurchase());
    }
    
    public void delete(Long id) {
        List<ProductDTO> cartProducts = cart.getPurchase();
        for (int i = 0; i < cartProducts.size(); i++) {
            if (cartProducts.get(i).getId().equals(id)) {
                cartProducts.remove(i);
            }
        }
    }
    
    public void clear() {
        cart.getPurchase().clear();
    }
    
}

package com.blck_rbbit.gbspringlessonschapter1.cartmstests;

import com.blck_rbbit.gbspringlessonschapter1.api.dto.CategoryDto;
import com.blck_rbbit.gbspringlessonschapter1.api.dto.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.services.CartService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartServiceTest {
    private final String TEST_CART_NAME = "test_cart";
    private final Integer TEST_COST_FOR_PRODUCT = (int) (Math.random() * (2001 - 57 + 1) + 57);
    private final Long TEST_PRODUCT_ID = (long) (Math.random() * (101 - 1 + 1) +1);
    
    @Autowired
    private CartService cartService;
    
    @BeforeEach
    public void initCart() {
        cartService.clearCart(TEST_CART_NAME);
    }
    
    @SneakyThrows
    @Test
    public void addToCartTest() {
        cartFillingWithTheSameProduct();
//  http://localhost:8701/core/api/v1/products/25
//        Mockito.verify(cartService, Mockito.times(5)).addToCart(ArgumentMatchers.eq(TEST_CART_NAME), ArgumentMatchers.eq(TEST_PRODUCT_ID));
            //todo эти проверки сравнивают с null, которое вернулось из cartService.getCurrentCart(String);
//        Assertions.assertEquals(1, cartService.getCurrentCart(TEST_CART_NAME).getItems().size());
//        Assertions.assertEquals(5, cartService.getCurrentCart(TEST_CART_NAME).getItems().get(0).getQuantity());
//        Assertions.assertEquals(
//                cartService.getCurrentCart(TEST_CART_NAME).getTotalPrice(),
//                (cartService.getCurrentCart(TEST_CART_NAME).getItems().get(0).getPricePerProduct()) * 5
//        );
    }
    
    private void cartFillingWithTheSameProduct() {
        ProductDto testedProduct = createTestedProduct(TEST_PRODUCT_ID);
        for (int i = 0; i < 5; i++) {
            cartService.addToCart(TEST_CART_NAME, testedProduct.getId());
        }
    }
    
    private ProductDto createTestedProduct(Long productId) {
        ProductDto product = new ProductDto();
        product.setId(productId);
        product.setTitle("exampleProduct");
        product.setCost(TEST_COST_FOR_PRODUCT);
        product.setCategoryDto(new CategoryDto(productId + 1, "exampleCategory"));
        return product;
    }
    
}

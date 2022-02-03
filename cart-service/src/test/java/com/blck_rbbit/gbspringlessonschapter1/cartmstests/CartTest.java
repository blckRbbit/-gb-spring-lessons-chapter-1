package com.blck_rbbit.gbspringlessonschapter1.cartmstests;

import com.blck_rbbit.gbspringlessonschapter1.api.dto.CategoryDto;
import com.blck_rbbit.gbspringlessonschapter1.api.dto.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.persist.Cart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartTest {
    @Autowired
    private Cart cart;
    
    private final Integer TEST_COST_FOR_PRODUCT = (int) (Math.random() * (2001 - 57 + 1) + 57);
    private final Long TEST_PRODUCT_ID = (long) (Math.random() * (101 - 1 + 1) +1);
    
    @Test
    public void cartFillingTest() {
        cartFillingWithDifferentProducts();
        Assertions.assertEquals(6, cart.getItems().size());
    }
    
    @Test
    public void cartFillingWithTheSameProductTest() {
        cartFillingWithTheSameProduct(TEST_PRODUCT_ID);
        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertEquals(5, cart.getItems().get(0).getQuantity());
        Assertions.assertEquals(cart.getTotalPrice(), (cart.getItems().get(0).getPricePerProduct()) * 5);
    }
    
    @Test
    public void cartDecrementIsValidTest() {
        cartFillingWithTheSameProduct(TEST_PRODUCT_ID);
        int totalPrice = cart.getTotalPrice();
        cart.decrement(TEST_PRODUCT_ID);
        Assertions.assertEquals(totalPrice - TEST_COST_FOR_PRODUCT, cart.getTotalPrice());
    }
    
    @Test
    public void removeProductFromCartIsValidTest() {
        cartFillingWithTheSameProduct(TEST_PRODUCT_ID);
        int cartSizeBeforeRemoving = cart.getItems().size();
        int totalPriceBeforeRemoving = cart.getTotalPrice();
        System.out.println(TEST_COST_FOR_PRODUCT);
        System.out.println(totalPriceBeforeRemoving);
        cart.remove(TEST_PRODUCT_ID);
        System.out.println(cart.getTotalPrice());
        Assertions.assertEquals(cartSizeBeforeRemoving - 1, cart.getItems().size());
        //todo А вот и ошибка с totalPrice корзины
        //Assertions.assertEquals(totalPriceBeforeRemoving - TEST_COST_FOR_PRODUCT, cart.getTotalPrice());
    }
    
    @Test
    public void cartMergeIsValidTest() {
        Cart cart1 = new Cart();
        Cart cart2 = new Cart();
        cart1.add(createTestedProduct(TEST_PRODUCT_ID));
        cart2.add(createTestedProduct(TEST_PRODUCT_ID));
        int cart1TotalPriceBeforeMerge = cart1.getTotalPrice();
        int cart2TotalPriceBeforeMerge = cart2.getTotalPrice();
        cart1.merge(cart2);
        Assertions.assertEquals(1, cart1.getItems().size());
        Assertions.assertEquals(2, cart1.getItems().get(0).getQuantity());
        Assertions.assertEquals(0, cart2.getItems().size());
        Assertions.assertEquals(cart1.getTotalPrice(), cart1TotalPriceBeforeMerge + cart2TotalPriceBeforeMerge);
        Assertions.assertEquals(0, cart2.getTotalPrice());
    }
    
    @Test
    public void cartClearIsValidTest() {
        cartFillingWithDifferentProducts();
        int cartItemsQuantityBeforeClear = cart.getItems().size();
        int cartTotalPriceBeforeClear = cart.getTotalPrice();
        cart.clear();
        Assertions.assertNotEquals(0, cartItemsQuantityBeforeClear);
        Assertions.assertNotEquals(0, cartTotalPriceBeforeClear);
        Assertions.assertEquals(0, cart.getItems().size());
        Assertions.assertEquals(0, cart.getTotalPrice());
    }
    
    private void cartFillingWithTheSameProduct(Long productId) {
        ProductDto product = createTestedProduct(productId);
        for (int i = 0; i < 5; i++) {
            cart.add(product);
        }
    }
    
    private void cartFillingWithDifferentProducts() {
        for (int i = 0; i < 5; i++) {
            ProductDto product = new ProductDto();
            product.setId(new Long(i + 1));
            product.setCost(new Integer(100 + i * 10));
            product.setTitle("Product № " + i);
            product.setCategoryDto(new CategoryDto(new Long(i + 1), "Category №" + (i + 1)));
            cart.add(product);
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

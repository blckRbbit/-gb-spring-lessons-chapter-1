package com.blck_rbbit.gbspringlessonschapter1.cartmstests;

import com.blck_rbbit.gbspringlessonschapter1.api.core.CategoryDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.models.Cart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartTest {
    private final Cart TESTED_CART = new Cart();
    private final Integer TEST_COST_FOR_PRODUCT = (int) (Math.random() * (2001 - 57 + 1) + 57);
    private final Long TEST_PRODUCT_ID = (long) (Math.random() * 100 +1);
    
    @Test
    public void cartFillingTest() {
        Cart cart = cartFillingWithDifferentProducts(TESTED_CART);
        Assertions.assertEquals(5, cart.getItems().size());
    }
    
    @Test
    public void cartFillingWithTheSameProductTest() {
        Cart cart = cartFillingWithTheSameProduct(TEST_PRODUCT_ID, TESTED_CART);
        Assertions.assertEquals(1, cart.getItems().size());
        Assertions.assertEquals(5, cart.getItems().get(0).getQuantity());
        Assertions.assertEquals(cart.getTotalPrice(), (cart.getItems().get(0).getPricePerProduct()) * 5);
    }

    @Test
    public void cartDecrementIsValidTest() {
        Cart cart = cartFillingWithTheSameProduct(TEST_PRODUCT_ID, TESTED_CART);
        int totalPrice = cart.getTotalPrice();
        cart.decrement(TEST_PRODUCT_ID);
        Assertions.assertEquals(totalPrice - TEST_COST_FOR_PRODUCT, cart.getTotalPrice());
    }

    @Test
    public void removeProductFromCartIsValidTest() {
        Cart cart = cartFillingWithDifferentProducts(TESTED_CART);
        ProductDto product = new ProductDto(1L, "Product № 1", 100, new CategoryDto(2L, "Category № 2"));
        cart.add(product);
        int cartSizeBeforeRemoving = cart.getItems().size();
        int totalPriceBeforeRemoving = cart.getTotalPrice();
        cart.remove(product.getId());
        Assertions.assertEquals(cartSizeBeforeRemoving - 1, cart.getItems().size());
        Assertions.assertEquals(totalPriceBeforeRemoving - (product.getCost() * 2), cart.getTotalPrice());
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
        Cart cart = cartFillingWithDifferentProducts(TESTED_CART);
        int cartItemsQuantityBeforeClear = cart.getItems().size();
        int cartTotalPriceBeforeClear = cart.getTotalPrice();
        cart.clear();
        Assertions.assertNotEquals(0, cartItemsQuantityBeforeClear);
        Assertions.assertNotEquals(0, cartTotalPriceBeforeClear);
        Assertions.assertEquals(0, cart.getItems().size());
        Assertions.assertEquals(0, cart.getTotalPrice());
    }
    
    private Cart cartFillingWithTheSameProduct(Long productId, Cart cart) {
        ProductDto product = createTestedProduct(productId);
        for (int i = 0; i < 5; i++) {
            cart.add(product);
        }
        return cart;
    }
    
    private Cart cartFillingWithDifferentProducts(Cart cart) {
        for (int i = 0; i < 5; i++) {
            ProductDto product = new ProductDto();
            product.setId(new Long(i + 1));
            product.setCost(new Integer(100 + i * 10));
            product.setTitle("Product № " + i);
            product.setCategoryDto(new CategoryDto(new Long(i + 1), "Category № " + (i + 1)));
            cart.add(product);
        }
        return cart;
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

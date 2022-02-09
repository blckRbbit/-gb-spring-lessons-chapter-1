package com.blck_rbbit.gbspringlessonschapter1.cartmstests;

import com.blck_rbbit.gbspringlessonschapter1.api.core.CategoryDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.integrations.CoreServiceIntegration;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.models.Cart;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CartServiceTest {
    private final String TEST_CART_NAME = "test_cart";
    private final Integer TEST_COST_FOR_PRODUCT = (int) (Math.random() * (2001 - 57 + 1) + 57);
    private final Long TEST_PRODUCT_ID = (long) (Math.random() * 20 +1);
    
    @Autowired
    private CartService cartService;
    
    @MockBean
    CoreServiceIntegration coreServiceIntegration;
    
    @BeforeEach
    public void initCart() {
        cartService.clearCart(TEST_CART_NAME);
    }
    
    @BeforeEach
    public void initCart2() {
        cartService.clearCart(TEST_CART_NAME + "_2");
    }
    
    @Test
    public void addToCartTest() {
        ProductDto product = createTestedProduct(TEST_PRODUCT_ID);
        Mockito.doReturn(product).when(coreServiceIntegration).getProductById(TEST_PRODUCT_ID);
        for (int i = 0; i < 5; i++) {
            cartService.addToCart(TEST_CART_NAME,coreServiceIntegration.getProductById(TEST_PRODUCT_ID).getId());
        }
        Assertions.assertEquals(1, cartService.getCurrentCart(TEST_CART_NAME).getItems().size());
        Assertions.assertEquals(5, cartService.getCurrentCart(TEST_CART_NAME).getItems().get(0).getQuantity());
        Assertions.assertEquals(
                cartService.getCurrentCart(TEST_CART_NAME).getTotalPrice(),
                (cartService.getCurrentCart(TEST_CART_NAME).getItems().get(0).getPricePerProduct()) * 5
        );
    }
    
    @Test
    public void clearCartTest() {
        Cart cart = cartService.getCurrentCart(TEST_CART_NAME);
        ProductDto product = createTestedProduct(TEST_PRODUCT_ID);
        cart.add(product.getId());
        cart.clear();
        Assertions.assertEquals(0, cart.getItems().size());
        Assertions.assertEquals(0, cart.getTotalPrice());
    }
    
    @Test
    public void removeItemFromCartTest() {
        Cart cart = cartService.getCurrentCart(TEST_CART_NAME);
        ProductDto product = createTestedProduct(TEST_PRODUCT_ID);
        ProductDto product2 = createTestedProduct(TEST_PRODUCT_ID + 1);
        for (int i = 0; i < 5; i++) {
            cart.add(product);
        }
        cart.add(product2);
        int totalPriceBeforeRemoved = cart.getTotalPrice();
        int quantityProductForCartBeforeRemoved = cart.getItems().size();
        cart.remove(product2.getId());
        Assertions.assertEquals(quantityProductForCartBeforeRemoved - 1, cart.getItems().size());
        Assertions.assertEquals(5, cart.getItems().get(0).getQuantity());
        Assertions.assertEquals(cart.getTotalPrice(), totalPriceBeforeRemoved - product2.getCost());
    }
    
    @Test
    public void cartDecrementTest() {
        Cart cart = cartService.getCurrentCart(TEST_CART_NAME);
        ProductDto product = createTestedProduct(TEST_PRODUCT_ID);
        for (int i = 0; i < 5; i++) {
            cart.add(product);
        }
        int totalPriceBeforeRemoved = cart.getTotalPrice();
        int quantityProductForCartBeforeRemoved = cart.getItems().size();
        int productQuantity = cart.getItems().get(0).getQuantity();
        cart.decrement(product.getId());
        Assertions.assertEquals(quantityProductForCartBeforeRemoved, cart.getItems().size());
        Assertions.assertEquals(cart.getTotalPrice(), totalPriceBeforeRemoved - product.getCost());
        Assertions.assertEquals(productQuantity - 1, cart.getItems().get(0).getQuantity());
    }
    
    @Test
    public void cartMergeTest() {
        Cart cart1 = cartService.getCurrentCart(TEST_CART_NAME);;
        Cart cart2 = cartService.getCurrentCart(TEST_CART_NAME + "_2");
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
    
    private ProductDto createTestedProduct(Long productId) {
        ProductDto product = new ProductDto();
        product.setId(productId);
        product.setTitle("exampleProduct");
        product.setCost(TEST_COST_FOR_PRODUCT);
        product.setCategoryDto(new CategoryDto(productId + 1, "exampleCategory"));
        return product;
    }
    
}

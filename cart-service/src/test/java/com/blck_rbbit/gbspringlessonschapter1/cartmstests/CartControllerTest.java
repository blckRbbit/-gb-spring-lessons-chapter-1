package com.blck_rbbit.gbspringlessonschapter1.cartmstests;

import com.blck_rbbit.gbspringlessonschapter1.api.cart.CartDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.CategoryDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.OrderItemDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.converters.CartConverter;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.models.Cart;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.services.CartService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CartControllerTest {
    private final String PATH = "/api/v1/cart/";
    private final String TEST_CART_NAME = "test_cart";
    private final Integer TEST_COST_FOR_PRODUCT = (int) (Math.random() * (2001 - 57 + 1) + 57);
    private final Long TEST_PRODUCT_ID = (long) (Math.random() * (101) +1);

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private CartConverter cartConverter;
    
    @BeforeEach
    public void initCart() {
        cartService.clearCart(TEST_CART_NAME);
    }

    @SneakyThrows
    @Test
    public void getCartTest() {
        OrderItemDto orderItemDto = new OrderItemDto(12L, "test_cart_item", 2, 100, 200, LocalDateTime.now());
        List<OrderItemDto> list = List.of(orderItemDto);
        Cart cart = new Cart(list, orderItemDto.getPrice());
        CartDto cartDto = cartConverter.modelToDto(cart);
        
        Mockito.doReturn(cart).when(cartService).getCurrentCart(TEST_CART_NAME);
        Mockito.doReturn(cartDto).when(cartService).getCurrentCart(TEST_CART_NAME);
        mvc
                .perform(get(PATH + TEST_CART_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$").isNotEmpty());
//                    .andExpect(jsonPath("$", hasSize(1)));
//                    .andExpect(jsonPath("$[0].title", is(allGenres.get(0).getTitle())));
    }
    @Test
    @SneakyThrows
    public void generateCartTest() {
        mvc
                .perform(get(PATH + "generate")
                        .contentType(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$").isNotEmpty())
                            .andExpect(jsonPath("$", hasItem(String.class)));
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

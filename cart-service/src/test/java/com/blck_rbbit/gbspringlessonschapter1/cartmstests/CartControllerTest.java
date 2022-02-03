package com.blck_rbbit.gbspringlessonschapter1.cartmstests;

import com.blck_rbbit.gbspringlessonschapter1.api.dto.CategoryDto;
import com.blck_rbbit.gbspringlessonschapter1.api.dto.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.converters.CartConverter;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.persist.Cart;
import com.blck_rbbit.gbspringlessonschapter1.cartapp.services.CartService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    private final String TEST_CART_NAME = "test_cart";
    private final Integer TEST_COST_FOR_PRODUCT = (int) (Math.random() * (2001 - 57 + 1) + 57);
    private final Long TEST_PRODUCT_ID = (long) (Math.random() * (101 - 1 + 1) +1);
    
    @Autowired
    private MockMvc mvc;
    
    @MockBean
    private CartService cartService;
    
    @MockBean
    private CartConverter cartConverter;
    
    @MockBean
    private Cart cart;
    
    @BeforeEach
    public void initCart() {
        cartService.clearCart(TEST_CART_NAME);
    }
    
    @SneakyThrows
    @Test
    public void getCartTest() {
        mvc
                .perform(get("/api/v1/cart/" + TEST_CART_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$").);
    //                .andExpect(jsonPath("$", hasSize(1)));
    //                .andExpect(jsonPath("$[0].title", is(allGenres.get(0).getTitle())));
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

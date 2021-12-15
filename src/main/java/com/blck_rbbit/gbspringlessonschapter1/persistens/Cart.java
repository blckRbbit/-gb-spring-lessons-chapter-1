package com.blck_rbbit.gbspringlessonschapter1.persistens;

import com.blck_rbbit.gbspringlessonschapter1.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private List<ProductDTO> purchase;
    private String title;
    private int quantity;
    private int price;
    
    @PostConstruct
    private void createCart() {
        purchase = new ArrayList<>();
    }
}

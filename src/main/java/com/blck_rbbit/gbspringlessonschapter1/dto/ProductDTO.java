package com.blck_rbbit.gbspringlessonschapter1.dto;

import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    @NotNull(message = "Enter product name")
    @Length(min = 3, max = 255, message = "Product name must be between 3 and 255 characters long")
    private String title;
    
    @Min(value = 1, message = "The price of the goods must be at least 1 ruble")
    private Integer cost;
}

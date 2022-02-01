package com.blck_rbbit.gbspringlessonschapter1.core.dto;

import com.blck_rbbit.gbspringlessonschapter1.core.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private Integer cost;
    private Category category;
}

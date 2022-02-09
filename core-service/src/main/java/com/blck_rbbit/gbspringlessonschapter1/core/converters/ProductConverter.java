package com.blck_rbbit.gbspringlessonschapter1.core.converters;

import com.blck_rbbit.gbspringlessonschapter1.api.core.CategoryDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDTO) {
        return new Product(productDTO.getId(), productDTO.getTitle(), productDTO.getCost());
    }
    
    public ProductDto entityToDTO(Product product) {
        CategoryDto categoryDto = new CategoryDto(product.getId(), product.getCategory().getTitle());
        return new ProductDto(product.getId(), product.getTitle(), product.getCost(), categoryDto);
    }
    
}

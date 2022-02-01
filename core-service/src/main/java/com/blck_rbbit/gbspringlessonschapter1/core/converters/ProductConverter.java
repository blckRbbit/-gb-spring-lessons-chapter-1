package com.blck_rbbit.gbspringlessonschapter1.core.converters;

import com.blck_rbbit.gbspringlessonschapter1.core.dto.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDTO) {
        return new Product(productDTO.getId(), productDTO.getTitle(), productDTO.getCost());
    }
    
    public ProductDto entityToDTO(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getCost(), product.getCategory());
    }
    
}

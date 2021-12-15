package com.blck_rbbit.gbspringlessonschapter1.converters;

import com.blck_rbbit.gbspringlessonschapter1.dto.ProductDTO;
import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDTO productDTO) {
        return new Product(productDTO.getId(), productDTO.getTitle(), productDTO.getCost());
    }
    
    public ProductDTO entityToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getTitle(), product.getCost());
    }
}

package com.blck_rbbit.gbspringlessonschapter1.validators;

import com.blck_rbbit.gbspringlessonschapter1.dto.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.exceptions.ProductDataValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDTO) {
        List<String> errors = new ArrayList<>();
        if (productDTO.getCost() < 1) {
            errors.add("The price of a product cannot be less than 1 ruble");
        }
        if (productDTO.getTitle().isBlank() || productDTO.getTitle().length() < 3) {
            errors.add("The product must have a title of at least 3 characters");
        }
        if (!errors.isEmpty()) {
            throw new ProductDataValidationException(errors);
        }
    }
}
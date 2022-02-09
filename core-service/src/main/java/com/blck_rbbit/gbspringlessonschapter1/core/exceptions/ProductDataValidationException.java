package com.blck_rbbit.gbspringlessonschapter1.core.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductDataValidationException extends RuntimeException {
    
    private List<String> errorFieldsMessages;
    
    public ProductDataValidationException(List<String> errorFieldsMessages) {
        super(String.join("\n", errorFieldsMessages));
        this.errorFieldsMessages = errorFieldsMessages;
    }
    
}

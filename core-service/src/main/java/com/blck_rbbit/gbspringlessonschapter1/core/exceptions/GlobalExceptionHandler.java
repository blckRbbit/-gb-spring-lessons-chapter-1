package com.blck_rbbit.gbspringlessonschapter1.core.exceptions;

import com.blck_rbbit.gbspringlessonschapter1.api.exceptions.AppError;
import com.blck_rbbit.gbspringlessonschapter1.api.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(String.valueOf(e));
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    public ResponseEntity<FieldsValidationError> catchProductDataValidationException(ProductDataValidationException e) {
        log.error(String.valueOf(e));
        return new ResponseEntity<>(new FieldsValidationError(e.getErrorFieldsMessages()),
                HttpStatus.BAD_REQUEST);
    }
}
package com.blck_rbbit.gbspringlessonschapter1.core.controllers;

import com.blck_rbbit.gbspringlessonschapter1.api.core.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.api.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.core.converters.ProductConverter;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.Product;
import com.blck_rbbit.gbspringlessonschapter1.core.services.ProductService;
import com.blck_rbbit.gbspringlessonschapter1.core.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;
    
    @GetMapping
    public Page<ProductDto> getAllPageProducts(
            @RequestParam(name = "min_cost", required = false) Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "title", required = false) String titlePart,
            @RequestParam(name = "categoryTitle", required = false) String categoryTitle,
            @RequestParam(name = "page", defaultValue = "1") Integer page
         
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.find(minCost, maxCost, id, titlePart, categoryTitle, page).map(
                productConverter::entityToDTO
        );
    }
    
    @GetMapping("/{id}")
    public ProductDto getById(@PathVariable Long id) {
        Product product = productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found, id: " + id));
        return productConverter.entityToDTO(product);
    }
    
    @PostMapping
    public ProductDto save(@RequestBody ProductDto productDTO) {
        productValidator.validate(productDTO);
        Product product = productConverter.dtoToEntity(productDTO);
        product = productService.save(product);
        return productConverter.entityToDTO(product);
    }
    
    @PutMapping
    public void update(@RequestBody ProductDto productDTO) {
        productValidator.validate(productDTO);
        productService.update(productDTO);
    }
    
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
    
}

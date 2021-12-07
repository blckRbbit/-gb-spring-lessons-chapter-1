package com.blck_rbbit.gbspringlessonschapter1.controllers;

import com.blck_rbbit.gbspringlessonschapter1.dto.ProductDTO;
import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import com.blck_rbbit.gbspringlessonschapter1.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    
    private ProductService productService;
    
    public ProductController() {
    }
    
    @GetMapping
    public Page<ProductDTO> getProducts(
            @RequestParam(name = "min_cost", required = false) Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost,
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "title", required = false) String titlePart,
            @RequestParam(name = "page", defaultValue = "1") Integer page
         
    ) {
        if (page < 1) page = 1;
        return productService.find(minCost, maxCost, id, titlePart, page).map(
                ProductDTO::new
        );
    }
    
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found, id: " + id)
        );
    }
    
    @PostMapping
    public Product saveNewProduct(@RequestBody ProductDTO productDTO) {
        productDTO.setId(null);
        Product product = new Product(productDTO.getTitle(), productDTO.getCost());
        return productService.save(product);
    }
    
    @PutMapping
    public Product updateProduct(@RequestBody ProductDTO productDTO) {
        Optional<Product> product = productService.findById(productDTO.getId());
        return product.map(value -> productService.save(value)).orElseThrow(
                () -> new ResourceNotFoundException("Product for update not found, id: " + productDTO.getId())
        );
    }
    
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
    
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    
}

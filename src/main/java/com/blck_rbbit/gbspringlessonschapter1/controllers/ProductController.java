package com.blck_rbbit.gbspringlessonschapter1.controllers;

import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import com.blck_rbbit.gbspringlessonschapter1.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    
    private ProductService productService;
    
    public ProductController() {
    }
    
    @GetMapping("/products/all")
    public List<Product> getProductsList() {
        return productService.findAllProducts();
    }
    
    @GetMapping("/products")
    public List<Product> getProductsForFirstPage() {
        return productService.findAllProductsByFirstPage();
    }
    
    @GetMapping("/products/next")
    public List<Product> getProductsForNextPage() {
        return productService.findAllProductsByNextPage();
    }
    
    @GetMapping("/products/previous")
    public List<Product> getProductsForPreviousPage() {
        return productService.findAllProductsByPreviousPage();
    }
    
    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found, id: " + id)
        );
    }
    
    @GetMapping("/products/delete/{id}")
    public List<Product> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return productService.findAllProducts();
    }
    
    @PostMapping("/products/change_cost")
    public void changeCost(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changeCost(productId, delta);
    }
    
    @GetMapping("/products/filter_by_cost")
    public List<Product> getFilteredProductsByCost
            (@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "2147483647") Integer max) {
        return productService.findAllProductsByPrice(min, max);
    }
    
    @PostMapping("/products")
    @ResponseBody
    public void addNewProduct(@RequestBody Product product) {
        productService.add(product);
    }
    
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
    
}

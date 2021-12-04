package com.blck_rbbit.gbspringlessonschapter1.services;

import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import com.blck_rbbit.gbspringlessonschapter1.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    private ProductRepository productRepository;
    private Pageable page = PageRequest.of(0, 5, Sort.by("id").ascending());;
    
    public ProductService() {
    }
    
    public List<Product> findAllProductsByFirstPage() {
        return productRepository.findAll(page).getContent();
    }
    
    public List<Product> findAllProductsByNextPage() {
        Pageable next = page.next();
        setPage(next);
        return productRepository.findAll(next).getContent();
    }
    
    public List<Product> findAllProductsByPreviousPage() {
        Pageable previous = page.previousOrFirst();
        setPage(previous);
        return productRepository.findAll(previous).getContent();
    }
    
    
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    public Page<Product> findByIdForPage(Long id) {
        return productRepository.findById(id, page );
    }
    
    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
    
    public List<Product> findAllProductsByPrice(Integer min, Integer max) {
        return productRepository.findAllByCostBetween(min, max);
    }
    
    public void add(Product product) {
        productRepository.save(product);
    }
    
    @Transactional
    public void changeCost(Long productId, Integer delta) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(
                "Unable to change product's cost. Product not found, id: " + productId)
        );
        product.setCost(delta);
        productRepository.save(product);
    }
    
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public void setPage(Pageable page) {
        this.page = page;
    }
    
}
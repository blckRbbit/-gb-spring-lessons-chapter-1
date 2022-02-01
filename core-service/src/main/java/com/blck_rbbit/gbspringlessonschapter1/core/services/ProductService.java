package com.blck_rbbit.gbspringlessonschapter1.core.services;

import com.blck_rbbit.gbspringlessonschapter1.api.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.core.dto.ProductDto;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.Product;
import com.blck_rbbit.gbspringlessonschapter1.core.repositories.ProductRepository;
import com.blck_rbbit.gbspringlessonschapter1.core.repositories.specifications.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public Page<Product> find(Integer minCost, Integer maxCost, Long id, String partTitle, String categoryTitle,
                              Integer page) {
        Specification<Product> specification = Specification.where(null);
        
        if (minCost != null) {
            specification = specification.and(ProductSpecifications.costGreaterOrEqualsThan(minCost));
        }
        if (maxCost != null) {
            specification = specification.and(ProductSpecifications.costLesserOrEqualsThan(maxCost));
        }
        if (id != null) {
            specification = specification.and(ProductSpecifications.genreIs(id));
        }
        if (partTitle != null) {
            specification = specification.and(ProductSpecifications.titleLike(partTitle));
        }
        if (categoryTitle != null) {
            specification = specification.and(ProductSpecifications.categoryTitleIs(categoryTitle));
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 5, Sort.by("id")));
    }
    
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    @Transactional
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
    
    @Transactional
    public void update(ProductDto productDTO) {
        Product product = findById(productDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product for update not found, id: " + productDTO.getId()));
        product.setTitle(productDTO.getTitle());
        product.setCost(productDTO.getCost());
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
}
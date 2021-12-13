package com.blck_rbbit.gbspringlessonschapter1.specifications;

import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecifications {
    
    public static Specification<Product> costGreaterOrEqualsThan(Integer cost) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                        .greaterThanOrEqualTo(root.get("cost"), cost);
    }
    
    public static Specification<Product> costLesserOrEqualsThan(Integer cost) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .lessThanOrEqualTo(root.get("cost"), cost);
    }
    
    public static Specification<Product> titleLike(String titlePart) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("title"), String.format("%%%s%%", titlePart));
    }
    
    public static Specification<Product> genreIs(Long id) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .equal(root.get("id"), id);
    }
}

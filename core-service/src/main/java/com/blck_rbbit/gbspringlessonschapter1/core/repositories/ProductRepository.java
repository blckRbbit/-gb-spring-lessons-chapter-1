package com.blck_rbbit.gbspringlessonschapter1.core.repositories;

import com.blck_rbbit.gbspringlessonschapter1.core.entities.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends
        JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
        
        // Page<Product> findAll(Specification<Category> specification, PageRequest id);
    
}
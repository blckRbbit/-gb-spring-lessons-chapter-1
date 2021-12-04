package com.blck_rbbit.gbspringlessonschapter1.repositories;

import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
     List<Product> findAllByCostBetween(Integer min, Integer max);
     Page<Product> findAll(Pageable pageable);
     Page<Product> findById(Long id, Pageable pageable);
     Page<Product> findAllByCostBetween(Integer min, Integer max, Pageable pageable);
}

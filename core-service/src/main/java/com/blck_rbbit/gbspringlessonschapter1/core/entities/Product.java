package com.blck_rbbit.gbspringlessonschapter1.core.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "cost", nullable = false)
    private Integer cost;
    
    @ManyToOne
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "category_id")
    private Category category;
    
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public Product(Long id, String title, Integer cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Product {id: %s, title: %s, cost: %s%n}", id, title, cost
        );
    }
    
}

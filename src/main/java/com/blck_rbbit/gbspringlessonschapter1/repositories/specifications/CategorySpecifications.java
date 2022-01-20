package com.blck_rbbit.gbspringlessonschapter1.repositories.specifications;

import com.blck_rbbit.gbspringlessonschapter1.entities.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecifications {
    public static Specification<Category> categoryTitleLike(String categoryTitlePart) {
        return (Specification<Category>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("title"), String.format("%%%s%%", categoryTitlePart));
    }
}

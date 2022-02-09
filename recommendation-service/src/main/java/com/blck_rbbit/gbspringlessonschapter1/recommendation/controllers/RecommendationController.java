package com.blck_rbbit.gbspringlessonschapter1.recommendation.controllers;

import com.blck_rbbit.gbspringlessonschapter1.api.cart.CartItemDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.OrderItemDto;
import com.blck_rbbit.gbspringlessonschapter1.recommendation.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommendations/top/")
@RequiredArgsConstructor
@Slf4j
public class RecommendationController {
    private final RecommendationService recommendationService;
    
    @GetMapping("month")
    public List<OrderItemDto> getTopOrderedProductsPerMonth() {
        return recommendationService.getTopOrderedProductsPerMonth();
    }
    
    @GetMapping("day")
    public List<CartItemDto> getTopProductsAddedToCartPerDay() {
        return recommendationService.getTopProductsAddedToCartPerDay();
    }
}

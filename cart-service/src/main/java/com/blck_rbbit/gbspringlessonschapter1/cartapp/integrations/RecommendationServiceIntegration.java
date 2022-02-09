package com.blck_rbbit.gbspringlessonschapter1.cartapp.integrations;

import com.blck_rbbit.gbspringlessonschapter1.api.cart.CartItemDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


@Component
@RequiredArgsConstructor
public class RecommendationServiceIntegration {
    private final WebClient recommendationServiceWebClient;
    
    public List<CartItemDto> getTopProductPerDay(Long id) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List response = recommendationServiceWebClient.get()
                .uri("/api/v1/recommendations/top/day")
                .retrieve()
                .bodyToMono(List.class)
                .block();
        return mapper.convertValue(response, new TypeReference<List<CartItemDto>>(){});
    }
}

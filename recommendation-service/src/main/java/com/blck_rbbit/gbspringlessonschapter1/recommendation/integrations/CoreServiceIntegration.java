package com.blck_rbbit.gbspringlessonschapter1.recommendation.integrations;

import com.blck_rbbit.gbspringlessonschapter1.api.core.OrderDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.ProductDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CoreServiceIntegration {
    private final WebClient coreServiceWebClient;
    
    public List<OrderDto> getAllOrders() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List response = coreServiceWebClient.get()
                .uri("/api/v1/orders/all")
                .retrieve()
                .bodyToMono(List.class)
                .block();
        return mapper.convertValue(response, new TypeReference<List<OrderDto>>(){});
    }
}

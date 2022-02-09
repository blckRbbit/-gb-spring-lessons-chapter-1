package com.blck_rbbit.gbspringlessonschapter1.cartapp.integrations;

import com.blck_rbbit.gbspringlessonschapter1.api.core.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CoreServiceIntegration {
    private final WebClient coreServiceWebClient;
    
    public ProductDto getProductById(Long id) {
        return coreServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .header("id", String.valueOf(id))
                .retrieve()
                .bodyToMono(ProductDto.class)
                .block();
    }
}

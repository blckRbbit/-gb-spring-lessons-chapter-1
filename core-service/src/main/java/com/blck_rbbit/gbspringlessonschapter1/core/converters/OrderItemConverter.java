package com.blck_rbbit.gbspringlessonschapter1.core.converters;

import com.blck_rbbit.gbspringlessonschapter1.core.dto.OrderItemDto;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {
    public OrderItem dtoToEntity(OrderItemDto orderItemDto) {
        throw new UnsupportedOperationException();
    }
    
    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getTitle(),
                orderItem.getQuantity(),
                orderItem.getPricePerProduct(),
                orderItem.getPrice());
    }
}

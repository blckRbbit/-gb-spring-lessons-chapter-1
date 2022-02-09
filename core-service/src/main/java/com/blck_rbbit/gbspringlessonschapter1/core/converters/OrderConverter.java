package com.blck_rbbit.gbspringlessonschapter1.core.converters;

import com.blck_rbbit.gbspringlessonschapter1.api.core.OrderDto;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;
    
    public Order dtoToEntity(OrderDto orderDto) {
        throw new UnsupportedOperationException();
    }
    
    public OrderDto entityToDto(Order order) {
        OrderDto out = new OrderDto();
        out.setId(order.getId());
        out.setAddress(order.getAddress());
        out.setPhone(order.getPhone());
        out.setTotalPrice(order.getTotalPrice());
        out.setUserName(order.getUsername());
        out.setItems(order.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()));
        out.setCreatedAt(LocalDateTime.now());
        return out;
    }
}

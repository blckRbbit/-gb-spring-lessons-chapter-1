package com.blck_rbbit.gbspringlessonschapter1.core.dto;

import com.blck_rbbit.gbspringlessonschapter1.api.dto.OrderItemDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String userName;
    private List<OrderItemDto> items;
    private Integer totalPrice;
    private String address;
    private String phone;
}

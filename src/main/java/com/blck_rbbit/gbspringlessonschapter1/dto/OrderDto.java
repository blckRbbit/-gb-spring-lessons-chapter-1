package com.blck_rbbit.gbspringlessonschapter1.dto;

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

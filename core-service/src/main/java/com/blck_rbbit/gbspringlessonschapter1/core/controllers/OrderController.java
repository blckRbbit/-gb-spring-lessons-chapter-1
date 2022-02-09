package com.blck_rbbit.gbspringlessonschapter1.core.controllers;

import com.blck_rbbit.gbspringlessonschapter1.core.converters.OrderConverter;
import com.blck_rbbit.gbspringlessonschapter1.api.core.OrderDetailsDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.OrderDto;
import com.blck_rbbit.gbspringlessonschapter1.core.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto) {
        orderService.createOrder(username, orderDetailsDto);
    }
    
    @GetMapping
    public List<OrderDto> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
    
    @GetMapping("/all")
    public List<OrderDto> getAllOrders() {
        return orderService.findAllOrders().stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
    
}

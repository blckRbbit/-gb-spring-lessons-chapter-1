package com.blck_rbbit.gbspringlessonschapter1.controllers;

import com.blck_rbbit.gbspringlessonschapter1.converters.OrderConverter;
import com.blck_rbbit.gbspringlessonschapter1.dto.OrderDetailsDto;
import com.blck_rbbit.gbspringlessonschapter1.dto.OrderDto;
import com.blck_rbbit.gbspringlessonschapter1.entities.User;
import com.blck_rbbit.gbspringlessonschapter1.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.services.OrderService;
import com.blck_rbbit.gbspringlessonschapter1.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(Principal principal, @RequestBody OrderDetailsDto orderDetailsDto) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));
        orderService.createOrder(user, orderDetailsDto);
    }
    
    @GetMapping
    public List<OrderDto> getCurrentUserOrders(Principal principal) {
        return orderService.findOrdersByUserName(principal.getName()).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}

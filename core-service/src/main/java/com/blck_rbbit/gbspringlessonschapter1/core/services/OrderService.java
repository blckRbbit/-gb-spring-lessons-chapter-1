package com.blck_rbbit.gbspringlessonschapter1.core.services;

import com.blck_rbbit.gbspringlessonschapter1.api.cart.CartDto;
import com.blck_rbbit.gbspringlessonschapter1.api.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.api.core.OrderDetailsDto;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.Order;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.OrderItem;
import com.blck_rbbit.gbspringlessonschapter1.core.integrations.CartServiceIntegration;
import com.blck_rbbit.gbspringlessonschapter1.core.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductService productService;
    
    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        CartDto currentCart = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUsername(username);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productService.findById(o.getProductId()).orElseThrow(
                            () -> new ResourceNotFoundException("Product not found"))
                    );
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
        cartServiceIntegration.clearUserCart(username);
    }
    
    public List<Order> findOrdersByUsername(String username) {
        return orderRepository.findAllByUsername(username);
    }
    
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }
}

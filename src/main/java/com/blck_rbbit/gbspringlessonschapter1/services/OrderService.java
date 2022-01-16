package com.blck_rbbit.gbspringlessonschapter1.services;

import com.blck_rbbit.gbspringlessonschapter1.dto.OrderDetailsDto;
import com.blck_rbbit.gbspringlessonschapter1.entities.Order;
import com.blck_rbbit.gbspringlessonschapter1.entities.OrderItem;
import com.blck_rbbit.gbspringlessonschapter1.entities.User;
import com.blck_rbbit.gbspringlessonschapter1.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.persistens.Cart;
import com.blck_rbbit.gbspringlessonschapter1.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;
    
    @Transactional
    public void createOrder(User user, OrderDetailsDto orderDetailsDto) {
        Cart currentCart = cartService.getCurrentCart();
        Order order = new Order();
        order.setAddress(orderDetailsDto.getAddress());
        order.setPhone(orderDetailsDto.getPhone());
        order.setUser(user);
        order.setTotalPrice(currentCart.getTotalPrice());
        List<OrderItem> items = currentCart.getItems().stream()
                .map(o -> {
                    OrderItem item = new OrderItem();
                    item.setOrder(order);
                    item.setQuantity(o.getQuantity());
                    item.setPricePerProduct(o.getPricePerProduct());
                    item.setPrice(o.getPrice());
                    item.setProduct(productService.findById(o.getProductId()).orElseThrow(
                            () -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
        currentCart.clear();
    }
    
    public List<Order> findOrdersByUserName(String userName) {
        return orderRepository.findAllByUsername(userName);
    }
}
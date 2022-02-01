package com.blck_rbbit.gbspringlessonschapter1.core.services;

import com.blck_rbbit.gbspringlessonschapter1.api.exceptions.ResourceNotFoundException;
import com.blck_rbbit.gbspringlessonschapter1.core.dto.Cart;
import com.blck_rbbit.gbspringlessonschapter1.core.dto.OrderDetailsDto;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.Order;
import com.blck_rbbit.gbspringlessonschapter1.core.entities.OrderItem;
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
    private final CartService cartService;
    private final ProductService productService;
    
    @Transactional
    public void createOrder(String username, OrderDetailsDto orderDetailsDto) {
        String cartKey = cartService.getCartUuidFromSuffix(username);
        Cart currentCart = cartService.getCurrentCart(cartKey);
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
                            () -> new ResourceNotFoundException("Product not found")));
                    return item;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
        cartService.clearCart(cartKey);
    }
    
    public List<Order> findOrdersByUserName(String userName) {
        return orderRepository.findAllByUsername(userName);
    }
}

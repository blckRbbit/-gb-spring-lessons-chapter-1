package com.blck_rbbit.gbspringlessonschapter1.recommendation.services;

import com.blck_rbbit.gbspringlessonschapter1.api.cart.CartItemDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.OrderDto;
import com.blck_rbbit.gbspringlessonschapter1.api.core.OrderItemDto;
import com.blck_rbbit.gbspringlessonschapter1.recommendation.integrations.CartServiceIntegration;
import com.blck_rbbit.gbspringlessonschapter1.recommendation.integrations.CoreServiceIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    
    private final CoreServiceIntegration coreServiceIntegration;
    private final CartServiceIntegration cartServiceIntegration;
    private final List<OrderItemDto> ORDER_ITEMS = new ArrayList<>();
    
    public List<OrderItemDto> getTopOrderedProductsPerMonth() {
        List<OrderDto> orders = coreServiceIntegration.getAllOrders();
        for (OrderDto order : orders) {
            ORDER_ITEMS.addAll(order.getItems());
        }
        List<OrderItemDto> result = new ArrayList<>();
        ORDER_ITEMS.sort(((o1, o2) -> (int) (o1.getProductId() - o2.getProductId())));
        for (int i = 0; i < ORDER_ITEMS.size() -1; i++) {
            OrderItemDto item = ORDER_ITEMS.get(i);
            OrderItemDto itemNext = ORDER_ITEMS.get(i + 1);
            if (item.getProductId().equals(itemNext.getProductId())) {
                int sum = itemNext.getQuantity() + item.getQuantity();
                itemNext.setQuantity(sum);
                ORDER_ITEMS.remove(item);
            }
        }
        ORDER_ITEMS.sort((o1, o2) -> o2.getQuantity() - o1.getQuantity());
        for (OrderItemDto orderItem : ORDER_ITEMS) {
            if (isDateWithInTheSpecifiedTimeInterval(orderItem)) {
                result.add(orderItem);
            }
        }
        ORDER_ITEMS.clear();
        return result;
    }
    
    private boolean isDateWithInTheSpecifiedTimeInterval(OrderItemDto item) {
        LocalDateTime currentDate = LocalDateTime.now();
        ChronoLocalDate firstDayOfMonth = currentDate.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate();
        ChronoLocalDate lastDayOfMonth = currentDate.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate();
        return !item
                .getCreatedAt().toLocalDate().isAfter(lastDayOfMonth) &&
                !item.getCreatedAt().toLocalDate().isBefore(firstDayOfMonth);
    }
    
    public List<CartItemDto> getTopProductsAddedToCartPerDay() {
        List<CartItemDto> temp = cartServiceIntegration.getCartItems();
        temp.sort(((o1, o2) -> (o2.getAdditionPerDay() - o1.getAdditionPerDay())));
        List<CartItemDto> result = temp.stream()
                .collect(Collectors.collectingAndThen(Collectors
                        .toCollection(() ->
                                new TreeSet<>(comparingLong(CartItemDto::getProductId))), ArrayList::new));
        result.sort(((o1, o2) -> (o2.getAdditionPerDay() - o1.getAdditionPerDay())));
        return result.size() >= 5 ? result.subList(0, 5) : result;
    }
    
}

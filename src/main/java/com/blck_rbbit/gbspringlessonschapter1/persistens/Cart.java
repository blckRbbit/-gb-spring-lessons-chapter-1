package com.blck_rbbit.gbspringlessonschapter1.persistens;

import com.blck_rbbit.gbspringlessonschapter1.dto.OrderItemDto;
import com.blck_rbbit.gbspringlessonschapter1.entities.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private int totalPrice;
    
    public Cart() {
        this.items = new ArrayList<>();
    }
    
    public void addProduct(Product product) {
        if (addProduct(product.getId())) {
            return;
        }
        items.add(new OrderItemDto(product));
        recalculate();
    }
    
    public boolean addProduct(Long id) {
        for (OrderItemDto item : items) {
            if (item.getProductId().equals(id)) {
                item.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }
    
    public void decreaseProduct(Long id) {
        Iterator<OrderItemDto> iterator = items.iterator();
        while (iterator.hasNext()) {
            OrderItemDto item = iterator.next();
            if (item.getProductId().equals(id)) {
                item.changeQuantity(-1);
                if (item.getQuantity() <= 0) {
                    iterator.remove();
                }
                recalculate();
                return;
            }
        }
    }
    
    public void removeProduct(Long id) {
        items.removeIf(i -> i.getProductId().equals(id));
        recalculate();
    }
    
    public void clear() {
        items.clear();
        totalPrice = 0;
    }
    
    private void recalculate() {
        totalPrice = 0;
        for (OrderItemDto item : items) {
            totalPrice += item.getPrice();
        }
    }
}

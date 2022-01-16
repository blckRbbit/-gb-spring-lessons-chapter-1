package com.blck_rbbit.gbspringlessonschapter1.repositories;

import com.blck_rbbit.gbspringlessonschapter1.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.user.username = ?1")
    List<Order> findAllByUsername(String userName);
}

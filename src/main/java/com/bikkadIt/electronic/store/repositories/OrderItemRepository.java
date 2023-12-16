package com.bikkadIt.electronic.store.repositories;

import com.bikkadIt.electronic.store.entities.Order;
import com.bikkadIt.electronic.store.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}

package com.challenge.funstore.domain.repository;

import com.challenge.funstore.domain.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
}

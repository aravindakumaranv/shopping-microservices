package com.project.shopping.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.shopping.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

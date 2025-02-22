package com.project.shopping.order.service;

import com.project.shopping.order.repository.OrderRepository;
import com.project.shopping.order.model.Order;
import com.project.shopping.order.dto.OrderRequest;
import com.project.shopping.order.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
            .skuCode(orderRequest.skuCode())
            .quantity(orderRequest.quantity())
            .price(orderRequest.price())
            .build();
        orderRepository.save(order);
        logger.info("Successfully created order with id: {}", order.getId());
        return new OrderResponse(
            order.getId(),
            order.getSkuCode(),
            order.getQuantity(),
            order.getPrice()
        );
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
            .map(order -> new OrderResponse(
                order.getId(),
                order.getSkuCode(),
                order.getQuantity(),
                order.getPrice()
            ))
            .collect(Collectors.toList());
    }
}

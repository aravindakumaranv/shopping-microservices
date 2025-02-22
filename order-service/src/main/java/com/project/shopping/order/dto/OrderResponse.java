package com.project.shopping.order.dto;

public record OrderResponse(Long id, String skuCode, int quantity, double price) {
}

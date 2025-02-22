package com.project.shopping.order.dto;

public record OrderRequest(String skuCode, int quantity, double price) {
}

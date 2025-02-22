package com.project.shopping.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.shopping.product.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByName(String name);
    Product findByCategory(String category);
    Product findByPrice(double price);
    Product findByDescription(String description);
}

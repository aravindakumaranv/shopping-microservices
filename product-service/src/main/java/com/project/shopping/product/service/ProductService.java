package com.project.shopping.product.service;

import com.project.shopping.product.repository.ProductRepository;
import com.project.shopping.product.model.Product;
import com.project.shopping.product.dto.ProductRequest;
import com.project.shopping.product.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
            .name(productRequest.name())
            .category(productRequest.category())
            .price(productRequest.price())
            .description(productRequest.description())
            .build();
        productRepository.save(product);
        logger.info("Successfully created product with name: {}", productRequest.name());
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getCategory(),
            product.getPrice(),
            product.getDescription()
        );
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
            .map(product -> new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getDescription()
            ))
            .collect(Collectors.toList());
    }
}

package com.project.shopping.inventory.repository;

import com.project.shopping.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityGreaterThan(String skuCode, int quantity);
}
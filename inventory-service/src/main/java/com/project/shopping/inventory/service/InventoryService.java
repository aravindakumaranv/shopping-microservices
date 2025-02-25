package com.project.shopping.inventory.service;

import com.project.shopping.inventory.repository.InventoryRepository;
import com.project.shopping.inventory.model.Inventory;
import com.project.shopping.inventory.dto.InventoryRequest;
import com.project.shopping.inventory.dto.InventoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryResponse> getAllInventory() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream()
            .map(inventory -> new InventoryResponse(
                inventory.getId(),
                inventory.getSkuCode(),
                inventory.getQuantity()
            ))
            .collect(Collectors.toList());
    }

    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
            .skuCode(inventoryRequest.skuCode())
            .quantity(inventoryRequest.quantity())
            .build();
        inventoryRepository.save(inventory);
        return new InventoryResponse(
            inventory.getId(),
            inventory.getSkuCode(),
            inventory.getQuantity()
        );
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    public boolean isInStock(String skuCode, int quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThan(skuCode, quantity);
    }
}

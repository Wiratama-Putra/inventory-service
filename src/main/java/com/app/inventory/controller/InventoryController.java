package com.app.inventory.controller;

import com.app.inventory.dto.OperationResponse;
import com.app.inventory.entity.Product;
import com.app.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return inventoryService.addProduct(product);
    }

    @PutMapping("/update/{productId}")
    public OperationResponse updateProductQuantity(@PathVariable Long productId, @RequestParam Integer quantity) {
        return inventoryService.updateProductQuantity(productId, quantity);
    }

    @GetMapping("/check/{productId}")
    public OperationResponse checkProductAvailability(@PathVariable Long productId) {
        return inventoryService.checkProductAvailability(productId);
    }

    @PostMapping("/order/{productId}")
    public OperationResponse orderProduct(@PathVariable Long productId, @RequestParam Integer quantity) {
        return inventoryService.orderProduct(productId, quantity);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return inventoryService.getAllProducts();
    }
}

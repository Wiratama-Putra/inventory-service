package com.app.inventory.controller;

import com.app.inventory.dto.OperationResponse;
import com.app.inventory.entity.Product;
import com.app.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Product addProduct(@RequestBody Product product) {
        return inventoryService.addProduct(product);
    }

    @PutMapping("/update/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public OperationResponse updateProductQuantity(@PathVariable Long productId, @RequestParam Integer quantity) {
        return inventoryService.updateProductQuantity(productId, quantity);
    }

    @GetMapping("/check/{productId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public OperationResponse checkProductAvailability(@PathVariable Long productId) {
        return inventoryService.checkProductAvailability(productId);
    }

    @PostMapping("/order/{productId}")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public OperationResponse orderProduct(@PathVariable Long productId, @RequestParam Integer quantity) {
        return inventoryService.orderProduct(productId, quantity);
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public List<Product> getAllProducts() {
        return inventoryService.getAllProducts();
    }
}

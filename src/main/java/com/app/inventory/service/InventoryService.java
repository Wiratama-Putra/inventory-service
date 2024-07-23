package com.app.inventory.service;

import com.app.inventory.dto.OperationResponse;
import com.app.inventory.entity.Product;
import com.app.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public OperationResponse updateProductQuantity(Long productId, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setQuantity(quantity);
            productRepository.save(product);
            return new OperationResponse("Quantity updated", true, product);
        } else {
            return new OperationResponse("Product not found", false);
        }
    }
    public OperationResponse checkProductAvailability(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return new OperationResponse("Product found", true, product.getQuantity());
        } else {
            return new OperationResponse("Product not found", false);
        }
    }

    public OperationResponse orderProduct(Long productId, Integer quantity) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getQuantity() >= quantity) {
                product.setQuantity(product.getQuantity() - quantity);
                productRepository.save(product);
                return new OperationResponse("Order successful", true, product);
            } else {
                return new OperationResponse("Insufficient stock", false);
            }
        } else {
            return new OperationResponse("Product not found", false);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

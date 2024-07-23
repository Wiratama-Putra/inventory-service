package com.app.inventory.service;


import com.app.inventory.dto.OperationResponse;
import com.app.inventory.entity.Product;
import com.app.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryService inventoryService;

    public InventoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(100.0);
        product.setQuantity(10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = inventoryService.addProduct(product);

        assertEquals(product.getName(), savedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProductQuantitySuccess() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100.0);
        product.setQuantity(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        OperationResponse response = inventoryService.updateProductQuantity(1L, 20);

        assertEquals("Quantity updated", response.getMessage());
        assertEquals(true, response.isSuccess());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProductQuantityNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        OperationResponse response = inventoryService.updateProductQuantity(1L, 20);

        assertEquals("Product not found", response.getMessage());
        assertEquals(false, response.isSuccess());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testCheckProductAvailabilityFound() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100.0);
        product.setQuantity(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        OperationResponse response = inventoryService.checkProductAvailability(1L);

        assertEquals("Product found", response.getMessage());
        assertEquals(true, response.isSuccess());
        assertEquals(10, response.getData());
    }

    @Test
    void testCheckProductAvailabilityNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        OperationResponse response = inventoryService.checkProductAvailability(1L);

        assertEquals("Product not found", response.getMessage());
        assertEquals(false, response.isSuccess());
    }

    @Test
    void testOrderProductSuccess() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100.0);
        product.setQuantity(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        OperationResponse response = inventoryService.orderProduct(1L, 5);

        assertEquals("Order successful", response.getMessage());
        assertEquals(true, response.isSuccess());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testOrderProductInsufficientStock() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(100.0);
        product.setQuantity(3);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        OperationResponse response = inventoryService.orderProduct(1L, 5);

        assertEquals("Insufficient stock", response.getMessage());
        assertEquals(false, response.isSuccess());
        verify(productRepository, times(0)).save(product);
    }

    @Test
    void testOrderProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        OperationResponse response = inventoryService.orderProduct(1L, 5);

        assertEquals("Product not found", response.getMessage());
        assertEquals(false, response.isSuccess());
        verify(productRepository, times(0)).save(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(100.0);
        product1.setQuantity(10);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(200.0);
        product2.setQuantity(20);

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> fetchedProducts = inventoryService.getAllProducts();

        assertEquals(2, fetchedProducts.size());
        verify(productRepository, times(1)).findAll();
    }
}

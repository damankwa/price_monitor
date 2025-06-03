package com.damankwa.price_monitor.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.damankwa.price_monitor.entity.MonitoredProduct;
import com.damankwa.price_monitor.repository.MonitoredProductRepository;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private MonitoredProductRepository productRepository;


    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
            "status", "UP",
            "message", "Price Monitor is running!",
            "totalProducts", productRepository.count()
        );
    }

    @GetMapping("/products")
    public List<MonitoredProduct> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    public MonitoredProduct createTestProduct() {
        MonitoredProduct product = new MonitoredProduct(
            "Test Motorola", 
            "https://example.com/motorola", 
            "test@example.com"
        );
        product.setCurrentPrice(new BigDecimal("999.99"));
        product.setTargetPrice(new BigDecimal("899.99"));

        return productRepository.save(product);
    }
}
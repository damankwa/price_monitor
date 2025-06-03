package com.damankwa.price_monitor.controller;

import com.damankwa.price_monitor.entity.MonitoredProduct;
import com.damankwa.price_monitor.repository.MonitoredProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
            "Test iPhone", 
            "https://example.com/iphone", 
            "test@example.com"
        );
        product.setCurrentPrice(new BigDecimal("999.99"));
        product.setTargetPrice(new BigDecimal("899.99"));

        return productRepository.save(product);
    }
}
package com.damankwa.price_monitor.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "monitored_products")
public class MonitoredProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "URL is required")
    @Column(nullable = false, unique = true, length = 2048)
    private String url;

    // ✅ FIXED: Initialize with empty HashMap
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "scraping_config", columnDefinition = "jsonb")
    private Map<String, Object> scrapingConfig = new HashMap<>();

    @Column(name = "current_price", precision = 10, scale = 2)
    private BigDecimal currentPrice;

    @Column(name = "target_price", precision = 10, scale = 2)
    private BigDecimal targetPrice;

    @Column(name = "last_checked")
    private LocalDateTime lastChecked;

    @Column(name = "check_frequency_minutes")
    private Integer checkFrequencyMinutes = 60;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private ProductStatus status = ProductStatus.ACTIVE;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "fallback_selectors", columnDefinition = "text[]")
    private String[] fallbackSelectors;

    // ✅ FIXED: Initialize with empty HashMap
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "site_settings", columnDefinition = "jsonb")
    private Map<String, Object> siteSettings = new HashMap<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor
    public MonitoredProduct() {
        // ✅ Ensure initialization in constructor too
        this.scrapingConfig = new HashMap<>();
        this.siteSettings = new HashMap<>();
    }

    // Constructor for easy creation
    public MonitoredProduct(String name, String url, String userEmail) {
        this(); // Call default constructor first
        this.name = name;
        this.url = url;
        this.userEmail = userEmail;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        // ✅ Ensure maps are never null before persisting
        if (this.scrapingConfig == null) {
            this.scrapingConfig = new HashMap<>();
        }
        if (this.siteSettings == null) {
            this.siteSettings = new HashMap<>();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // All your getters and setters remain the same...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Map<String, Object> getScrapingConfig() { 
        return scrapingConfig != null ? scrapingConfig : new HashMap<>(); 
    }

    public void setScrapingConfig(Map<String, Object> scrapingConfig) { 
        this.scrapingConfig = scrapingConfig != null ? scrapingConfig : new HashMap<>(); 
    }

    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }

    public BigDecimal getTargetPrice() { return targetPrice; }
    public void setTargetPrice(BigDecimal targetPrice) { this.targetPrice = targetPrice; }

    public LocalDateTime getLastChecked() { return lastChecked; }
    public void setLastChecked(LocalDateTime lastChecked) { this.lastChecked = lastChecked; }

    public Integer getCheckFrequencyMinutes() { return checkFrequencyMinutes; }
    public void setCheckFrequencyMinutes(Integer checkFrequencyMinutes) { this.checkFrequencyMinutes = checkFrequencyMinutes; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public ProductStatus getStatus() { return status; }
    public void setStatus(ProductStatus status) { this.status = status; }

    public String[] getFallbackSelectors() { return fallbackSelectors; }
    public void setFallbackSelectors(String[] fallbackSelectors) { this.fallbackSelectors = fallbackSelectors; }

    public Map<String, Object> getSiteSettings() { 
        return siteSettings != null ? siteSettings : new HashMap<>(); 
    }

    public void setSiteSettings(Map<String, Object> siteSettings) { 
        this.siteSettings = siteSettings != null ? siteSettings : new HashMap<>(); 
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "MonitoredProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", currentPrice=" + currentPrice +
                ", status=" + status +
                '}';
    }
}

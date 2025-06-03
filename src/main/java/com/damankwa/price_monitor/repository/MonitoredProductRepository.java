package com.damankwa.price_monitor.repository;

import com.damankwa.price_monitor.entity.MonitoredProduct;
import com.damankwa.price_monitor.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonitoredProductRepository extends JpaRepository<MonitoredProduct, Long> {

    // Find by status
    List<MonitoredProduct> findByStatus(ProductStatus status);

    // Find by user email
    List<MonitoredProduct> findByUserEmail(String userEmail);

    // Find products that need checking
    @Query("SELECT p FROM MonitoredProduct p WHERE p.status = 'ACTIVE' AND " +
        "(p.lastChecked IS NULL OR p.lastChecked < :cutoffTime)")
    List<MonitoredProduct> findProductsDueForCheck(@Param("cutoffTime") LocalDateTime cutoffTime);

    // Find by URL (useful for avoiding duplicates)
    Optional<MonitoredProduct> findByUrl(String url);

    // Count by status
    @Query("SELECT p.status, COUNT(p) FROM MonitoredProduct p GROUP BY p.status")
    List<Object[]> countByStatus();
}

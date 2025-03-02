package com.cry.ecom.Repository;

import com.cry.ecom.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  List<OrderItem> findByProductId(Long id);
}

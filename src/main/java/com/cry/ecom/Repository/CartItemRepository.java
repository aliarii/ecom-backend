package com.cry.ecom.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cry.ecom.Entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);

    List<CartItem> findByProductId(Long productId);
}

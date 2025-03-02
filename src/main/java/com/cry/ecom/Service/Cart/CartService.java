package com.cry.ecom.Service.Cart;

import com.cry.ecom.Dto.CartDto;
import com.cry.ecom.Entity.Cart;
import com.cry.ecom.Entity.User;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);

    CartDto convertToDto(Cart cart);
}

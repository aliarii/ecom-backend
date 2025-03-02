package com.cry.ecom.Service.Cart;

import java.math.BigDecimal;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cry.ecom.Dto.CartDto;
import com.cry.ecom.Entity.Cart;
import com.cry.ecom.Entity.User;
import com.cry.ecom.Exception.ResourceNotFoundException;
import com.cry.ecom.Repository.CartItemRepository;
import com.cry.ecom.Repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;

    @Override
    public Cart getCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    /*
     * @Override
     * public Cart getCart(Long userId) {
     * return Optional.ofNullable(getCartByUserId(userId))
     * .map(cart -> {
     * // If the cart exists, return it
     * BigDecimal totalAmount = cart.getTotalAmount();
     * cart.setTotalAmount(totalAmount);
     * return cartRepository.save(cart);
     * })
     * .orElseGet(() -> {
     * // If the cart does not exist, initialize a new cart
     * User user = Optional.ofNullable(userService.getUserById(userId))
     * .orElseThrow(() -> new ResourceNotFoundException("User not found"));
     * return initializeNewCart(user);
     * });
     * }
     */

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.clearCart();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public CartDto convertToDto(Cart cart) {
        return modelMapper.map(cart, CartDto.class);
    }
}

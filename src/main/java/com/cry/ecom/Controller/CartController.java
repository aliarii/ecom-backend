package com.cry.ecom.Controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cry.ecom.Dto.CartDto;
import com.cry.ecom.Dto.Response.ApiResponse;
import com.cry.ecom.Entity.Cart;
import com.cry.ecom.Exception.ResourceNotFoundException;
import com.cry.ecom.Service.Cart.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final CartService cartService;

    /*
     * @GetMapping("/{cartId}/my-cart")
     * public ResponseEntity<ApiResponse> getCart( @PathVariable Long cartId) {
     * try {
     * Cart cart = cartService.getCart(cartId);
     * CartDto cartDto = cartService.convertToDto(cart);
     * return ResponseEntity.ok(new ApiResponse("Success", cartDto));
     * } catch (ResourceNotFoundException e) {
     * return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),
     * null));
     * }
     * }
     */

    @GetMapping("/user/{userId}/my-cart")
    public ResponseEntity<ApiResponse> getUserCart(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            CartDto cartDto = cartService.convertToDto(cart);
            return ResponseEntity.ok(new ApiResponse("Success", cartDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear Cart Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
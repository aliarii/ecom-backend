package com.cry.ecom.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private Long cartId;
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;
}

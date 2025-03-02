package com.cry.ecom.Dto.Request;

import java.math.BigDecimal;

import com.cry.ecom.Entity.ProductCategory;

import lombok.Data;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private ProductCategory productCategory;
}

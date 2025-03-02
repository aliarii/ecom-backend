package com.cry.ecom.Dto.Request;

import com.cry.ecom.Entity.ProductCategory;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private ProductCategory productCategory;
}

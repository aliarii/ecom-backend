package com.cry.ecom.Dto;

import java.math.BigDecimal;
import java.util.List;

import com.cry.ecom.Entity.ProductCategory;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private ProductCategory productCategory;
    private List<ImageDto> images;
}

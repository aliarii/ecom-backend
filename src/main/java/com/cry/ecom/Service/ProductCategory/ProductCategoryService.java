package com.cry.ecom.Service.ProductCategory;

import java.util.List;

import com.cry.ecom.Entity.ProductCategory;

public interface ProductCategoryService {
    ProductCategory getCategoryById(Long id);

    ProductCategory getCategoryByName(String name);

    List<ProductCategory> getAllCategories();

    ProductCategory addCategory(ProductCategory productCategory);

    ProductCategory updateCategory(ProductCategory productCategory, Long id);

    void deleteCategoryById(Long id);

}

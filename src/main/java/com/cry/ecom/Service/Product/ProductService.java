package com.cry.ecom.Service.Product;

import com.cry.ecom.Dto.ProductDto;
import com.cry.ecom.Entity.Product;
import com.cry.ecom.Dto.Request.AddProductRequest;
import com.cry.ecom.Dto.Request.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String productCategory);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String productCategory, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String productCategory, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);

    // List<Product> getDistinctProductsByCategory();

    List<Product> findDistinctProductsByName();

    List<String> getAllDistinctBrands();
}

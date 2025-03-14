package com.cry.ecom.Repository;

import com.cry.ecom.Entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
  ProductCategory findByName(String name);

  boolean existsByName(String name);
}

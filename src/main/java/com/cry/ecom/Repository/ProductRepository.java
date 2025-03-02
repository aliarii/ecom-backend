package com.cry.ecom.Repository;

import com.cry.ecom.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductCategoryName(String productCategory);

    List<Product> findByBrand(String brand);

    List<Product> findByProductCategoryNameAndBrand(String productCategory, String brand);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Product> findByName(@Param("name") String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countByBrandAndName(String brand, String name);

    boolean existsByNameAndBrand(String name, String brand);
}

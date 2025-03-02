package com.cry.ecom.Service.ProductCategory;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cry.ecom.Entity.ProductCategory;
import com.cry.ecom.Exception.AlreadyExistsException;
import com.cry.ecom.Exception.ResourceNotFoundException;
import com.cry.ecom.Repository.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory getCategoryById(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public ProductCategory getCategoryByName(String name) {
        return productCategoryRepository.findByName(name);
    }

    @Override
    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategory addCategory(ProductCategory productCategory) {
        return Optional.of(productCategory).filter(c -> !productCategoryRepository.existsByName(c.getName()))
                .map(productCategoryRepository::save)
                .orElseThrow(() -> new AlreadyExistsException(productCategory.getName() + " already exists"));
    }

    @Override
    public ProductCategory updateCategory(ProductCategory productCategory, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(productCategory.getName());
            return productCategoryRepository.save(oldCategory);
        }).orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        productCategoryRepository.findById(id)
                .ifPresentOrElse(productCategoryRepository::delete, () -> {
                    throw new ResourceNotFoundException("Category not found!");
                });

    }
}

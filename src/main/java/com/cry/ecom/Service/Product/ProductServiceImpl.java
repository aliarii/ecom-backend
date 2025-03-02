package com.cry.ecom.Service.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.cry.ecom.Dto.ImageDto;
import com.cry.ecom.Dto.ProductDto;
import com.cry.ecom.Dto.Request.AddProductRequest;
import com.cry.ecom.Dto.Request.ProductUpdateRequest;
import com.cry.ecom.Entity.CartItem;
import com.cry.ecom.Entity.Image;
import com.cry.ecom.Entity.OrderItem;
import com.cry.ecom.Entity.Product;
import com.cry.ecom.Entity.ProductCategory;
import com.cry.ecom.Exception.AlreadyExistsException;
import com.cry.ecom.Exception.ResourceNotFoundException;
import com.cry.ecom.Repository.CartItemRepository;
import com.cry.ecom.Repository.ImageRepository;
import com.cry.ecom.Repository.OrderItemRepository;
import com.cry.ecom.Repository.ProductCategoryRepository;
import com.cry.ecom.Repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        if (productExists(request.getName(), request.getBrand())) {
            throw new AlreadyExistsException(request.getBrand() + " "
                    + request.getName() + " already exists, you may update this product instead!");
        }
        ProductCategory productCategory = Optional
                .ofNullable(productCategoryRepository.findByName(request.getProductCategory().getName()))
                .orElseGet(() -> {
                    ProductCategory newCategory = new ProductCategory(request.getProductCategory().getName());
                    return productCategoryRepository.save(newCategory);
                });
        request.setProductCategory(productCategory);
        return productRepository.save(createProduct(request, productCategory));
    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private Product createProduct(AddProductRequest request, ProductCategory productCategory) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                productCategory);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        List<CartItem> cartItems = cartItemRepository.findByProductId(id);
        List<OrderItem> orderItems = orderItemRepository.findByProductId(id);
        productRepository.findById(id)
                .ifPresentOrElse(product -> {
                    // Functional approach for productCategory removal
                    Optional.ofNullable(product.getProductCategory())
                            .ifPresent(productCategory -> productCategory.getProducts().remove(product));
                    product.setProductCategory(null);

                    // Functional approach for updating cart items
                    cartItems.stream()
                            .peek(cartItem -> cartItem.setProduct(null))
                            .peek(CartItem::setTotalPrice)
                            .forEach(cartItemRepository::save);

                    // Functional approach for updating order items
                    orderItems.stream()
                            .peek(orderItem -> orderItem.setProduct(null))
                            .forEach(orderItemRepository::save);

                    productRepository.delete(product);
                }, () -> {
                    throw new EntityNotFoundException("Product not found!");
                });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        ProductCategory productCategory = productCategoryRepository.findByName(request.getProductCategory().getName());
        existingProduct.setProductCategory(productCategory);
        return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String productCategory) {
        return productRepository.findByProductCategoryName(productCategory);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String productCategory, String brand) {
        return productRepository.findByProductCategoryNameAndBrand(productCategory, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }

    @Override
    public List<Product> findDistinctProductsByName() {
        List<Product> products = productRepository.findAll();
        Map<String, Product> distinctProductsMap = products.stream()
                .collect(Collectors.toMap(
                        Product::getName,
                        product -> product,
                        (existing, replacement) -> existing));
        return new ArrayList<>(distinctProductsMap.values());
    }

    @Override
    public List<String> getAllDistinctBrands() {
        return productRepository.findAll().stream()
                .map(Product::getBrand)
                .distinct()
                .collect(Collectors.toList());
    }

}

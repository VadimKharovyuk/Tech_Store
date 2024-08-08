// ProductFeignClient.java
package com.example.webservice.repository;

import com.example.webservice.dto.Category;
import com.example.webservice.dto.Product;
import com.example.webservice.dto.ReviewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "product-service")
public interface ProductFeignClient {
    @GetMapping("/api/products")
    List<Product> getAllProducts();

    @PostMapping("/api/products/add")
    Product save(@RequestBody Product product);

    @GetMapping("/api/products/category/{categoryId}")
    List<Product> getProductsByCategory(@PathVariable Long categoryId);

    @GetMapping("/api/products/categories")
    List<Category> getAllCategories();

    @PostMapping("/api/products/categories/add")
    Category saveCategory(@RequestBody Category category);


    @PostMapping("/api/products/delete/{id}")
    void deleteProductById(@PathVariable Long id);

    @PostMapping("/api/products/delete/category/{id}")
    void deleteCategoryById(@PathVariable Long id);


    @GetMapping("/api/products/search")
    List<Product> searchProductsByName(@RequestParam String name);


    @GetMapping("/api/products/edit/{id}")
    Product getproductById(@PathVariable Long id);

    @PostMapping("/api/products/edit/{id}")
    Product updateProduct(@PathVariable Long id,@RequestBody Product product);




    @PostMapping("/reviews")
    ReviewDTO createReview(@RequestBody ReviewDTO reviewDTO);

    @GetMapping("/reviews/{id}")
    Optional<ReviewDTO> getReview(@PathVariable("id") Long id);


    @DeleteMapping("/reviews/{id}")
    void deleteReview(@PathVariable("id") Long id);

    @GetMapping("/reviews/product/{productId}")
    List<ReviewDTO> getReviewsByProductId(@PathVariable("productId") Long productId);


}



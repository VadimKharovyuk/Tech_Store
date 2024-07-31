// ProductController.java
package com.example.webservice.controller;

import com.example.webservice.dto.Category;
import com.example.webservice.dto.Product;
import com.example.webservice.repository.ProductFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private final ProductFeignClient productFeignClient;

    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = productFeignClient.getAllProducts();
        List<Category> categoryList = productFeignClient.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("category", categoryList);

        return "products";
    }


    @GetMapping("/products/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productFeignClient.getAllCategories());
        return "newproducts";
    }

    @PostMapping("/products/add")
    public String save(@ModelAttribute Product product) {
        productFeignClient.save(product);
        return "redirect:/products";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        List<Category> categories = productFeignClient.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/products/category/{categoryId}")
    public String getProductsByCategory(@PathVariable Long categoryId, Model model) {
        List<Product> products = productFeignClient.getProductsByCategory(categoryId);
        model.addAttribute("products", products);
        return "products";
    }
}

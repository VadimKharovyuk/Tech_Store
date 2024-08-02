// ProductController.java
package com.example.webservice.controller;

import com.example.webservice.dto.Category;
import com.example.webservice.dto.Product;
import com.example.webservice.repository.ProductFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "products/products";
    }


    @GetMapping("/products/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", productFeignClient.getAllCategories());
        return "products/newproducts";
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
        return "category/categories";
    }

    @GetMapping("/products/category/{categoryId}")
    public String getProductsByCategory(@PathVariable Long categoryId, Model model) {
        List<Product> products = productFeignClient.getProductsByCategory(categoryId);
        model.addAttribute("products", products);
        return "products/products";
    }
    @GetMapping("/categories/new")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/newcategory";
    }
    @PostMapping("/categories/add")
    public String saveCategory(@ModelAttribute Category category) {
        productFeignClient.saveCategory(category);
        return "redirect:/categories";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productFeignClient.deleteProductById(id);
        redirectAttributes.addFlashAttribute("message", "Product deleted successfully");
        return "redirect:/products";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        productFeignClient.deleteCategoryById(id);
        return "redirect:/categories";
    }



}

// ProductController.java
package com.example.webservice.controller;
import com.example.webservice.dto.Category;
import com.example.webservice.dto.Product;
import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.ProductFeignClient;
import com.example.webservice.repository.UserFeignClient;
import com.example.webservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private final ProductFeignClient productFeignClient;
    private final ProductService productService;
    private final UserFeignClient userFeignClient;

    @GetMapping("/products")
    public String getProducts(Model model) {
//        List<Product> products = productFeignClient.getAllProducts();
        List<Category> categoryList = productFeignClient.getAllCategories();
//        model.addAttribute("products", products);
        model.addAttribute("category", categoryList);
//        return "products/products";

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

//            // Получение информации о пользователе
//            UserDTO user = userFeignClient.getUserByUsername(username);
//            Long userId = user.getId();
//
//            // Добавляем userId в модель
//            model.addAttribute("userId", userId);

            // Получение списка продуктов
            List<Product> products = productFeignClient.getAllProducts();
            model.addAttribute("products", products);

            return "products/products"; // имя HTML-шаблона
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
        return "redirect:/admin/products";
    }

    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        productFeignClient.deleteCategoryById(id);
        return "redirect:/categories";
    }

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(required = false) String name, Model model) {
        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("error", "Search term is required");
            return "products/search";
        }
        List<Product> products = productFeignClient.searchProductsByName(name);
        model.addAttribute("products", products);
        return "products/search";
    }

@GetMapping("/products/edit/{id}")
    public String getProductById(@PathVariable Long id,Model model){
    Product product = productService.getProduct(id);
    List<Category> categories = productFeignClient.getAllCategories(); // Метод для получения всех категорий
    model.addAttribute("product", product);
    model.addAttribute("categories", categories);
    return "products/product-edit";
}
@PostMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable Long id,@ModelAttribute Product product){
        productService.updateProduct(id,product);
    return "redirect:/admin/products";
}


}

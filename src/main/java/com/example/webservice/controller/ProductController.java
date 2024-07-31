package com.example.webservice.controller;

import com.example.webservice.dto.Product;
import com.example.webservice.repository.ProductFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductFeignClient productFeignClient;
    @GetMapping("/products")
    public String getProducts(Model model) {
        List<Product> products = productFeignClient.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/products/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product()); // Обратите внимание на изменение имени атрибута
        return "newproducts";
    }

    @PostMapping("/products/add")
    public String save(@ModelAttribute Product product) {
        productFeignClient.save(product); // сохраним продукт через FeignClient
        return "redirect:/products"; // перенаправление на страницу продуктов
    }


}



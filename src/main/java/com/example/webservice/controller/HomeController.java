package com.example.webservice.controller;

import com.example.webservice.dto.Product;
import com.example.webservice.repository.ProductFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {
    private final ProductFeignClient productFeignClient;


    @GetMapping
    public String homePage(@RequestParam(required = false, defaultValue = "") String name, Model model) {
        List<Product> products = productFeignClient.searchProductsByName(name);
        model.addAttribute("products", products);
        model.addAttribute("categories", productFeignClient.getAllCategories());
        return "homePage";
    }

}

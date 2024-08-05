package com.example.webservice.controller;

import com.example.webservice.repository.ProductFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private final ProductFeignClient productFeignClient;


    @GetMapping
    public String homePage(Model model){
       model.addAttribute("categories" ,productFeignClient.getAllCategories());
        return "homePage";
    }
}

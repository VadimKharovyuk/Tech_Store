package com.example.webservice.controller;

import com.example.webservice.dto.Product;
import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.ProductFeignClient;
import com.example.webservice.repository.UserFeignClient;
import com.example.webservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {
    private final ProductFeignClient productFeignClient;
   private final UserService userService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        // Вы можете добавить атрибуты в модель, если нужно
        return "admin/admin-dashboard";
    }
    @GetMapping("/products")
    public String productList(Model model){
        List<Product> productList = productFeignClient.getAllProducts();
      model.addAttribute("products",productList);
      return "admin/products-admin";

    }
    @GetMapping("/users")
    public String userList(Model model) {
        List<UserDTO> userDTOList = userService.userDTOList();
        model.addAttribute("users", userDTOList);
        return "admin/userDTOlist";
    }


}

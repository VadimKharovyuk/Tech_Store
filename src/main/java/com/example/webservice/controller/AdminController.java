package com.example.webservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        // Вы можете добавить атрибуты в модель, если нужно
        return "admin/admin-dashboard";
    }

    // Добавьте другие методы для админских функций здесь
}

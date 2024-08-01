package com.example.webservice.controller;

import com.example.webservice.repository.UserFeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {

    private final UserFeignClient userFeignClient;

    public PasswordController(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "account/change-password"; // Имя HTML-шаблона для формы
    }

    @PostMapping("/users/change-password")
    public String changePassword(
            @RequestParam String username,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        // Вызов метода через FeignClient
        userFeignClient.changePassword(username, currentPassword, newPassword);
        return "redirect:/account"; // Перенаправление на страницу со списком пользователей или личный кабинет
    }
}

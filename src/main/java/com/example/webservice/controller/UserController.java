package com.example.webservice.controller;

import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.UserFeignClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserFeignClient userFeignClient;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "user/Login";
    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            ResponseEntity<String> response = userFeignClient.login(username, password);

            if (response.getStatusCode() == HttpStatus.OK) {
                model.addAttribute("message", "Login successful");
                return "redirect:/";
            } else {
                model.addAttribute("error", "Invalid credentials");
                return "redirect:/";
            }
        } catch (FeignException e) {
            model.addAttribute("error", "Login failed: " + e.getMessage());
            return "redirect:/";
        }
    }


    @GetMapping("/register")
    public String registerFrom(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "user/register";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserDTO> users = userFeignClient.getAllUsers();
        model.addAttribute("users", users);
        return "users"; // Имя HTML-шаблона для отображения списка пользователей
    }

    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute UserDTO userDTO) {
        userFeignClient.registerUser(userDTO);
        return "redirect:/";
    }

    @PostMapping("/users/change-password")
    public String changePassword(
            @RequestParam String username,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        userFeignClient.changePassword(username, currentPassword, newPassword);
        return "redirect:/users";
    }

    @PostMapping("/users/block/{userId}")
    public String blockUser(@PathVariable Long userId) {
        userFeignClient.blockUser(userId);
        return "redirect:/users";
    }

    @PostMapping("/users/unblock/{userId}")
    public String unblockUser(@PathVariable Long userId) {
        userFeignClient.unblockUser(userId);
        return "redirect:/users";
    }

    @GetMapping("/users/is-blocked")
    public String isUserBlocked(@RequestParam String username, Model model) {
        Boolean isBlocked = userFeignClient.isBlocked(username);
        model.addAttribute("isBlocked", isBlocked);
        return "user-status"; // Имя HTML-шаблона для отображения статуса пользователя
    }
}

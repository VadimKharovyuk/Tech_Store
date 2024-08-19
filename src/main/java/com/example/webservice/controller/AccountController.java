package com.example.webservice.controller;

import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.UserFeignClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AccountController {
    private final UserFeignClient userFeignClient;

//@GetMapping("/account")
//public String accountUser(Model model) {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    String username = authentication.getName();
//    boolean isAdmin = authentication.getAuthorities().stream()
//            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
//
//    // Пример создания объекта UserDTO и добавления его в модель
//    UserDTO userDTO = new UserDTO();
//    userDTO.setUsername(username);
//    userDTO.setRole(isAdmin ? "ADMIN" : "USER"); // Пример присвоения роли
//
//    model.addAttribute("user", userDTO);
//    model.addAttribute("name", username);
//
//    return "account/account";
//}

//    @GetMapping("/account")
//    public String accountUser(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username;
//        String email;
//        String role;
//
//        // Проверьте, что authentication содержит OAuth2User
//        if (authentication.getPrincipal() instanceof OAuth2User) {
//            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
//            username = oauthUser.getAttribute("name");
//            email = oauthUser.getAttribute("email");
//            role = "USER"; // Вы можете определить роль как "USER" по умолчанию или на основе другой логики
//        } else {
//            // Если не OAuth2User, используйте имя пользователя из контекста безопасности
//            username = authentication.getName();
//            email = "unknown"; // Или другой способ получения email
//            role = "USER"; // Или другая логика для роли
//        }
//
//        boolean isAdmin = authentication.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
//
//        // Создание объекта UserDTO
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(username);
//        userDTO.setEmail(email);
//        userDTO.setRole(isAdmin ? "ADMIN" : role);
//        userDTO.setBlocked(false); // Установите значение по умолчанию или получите его из базы данных
//
//        model.addAttribute("user", userDTO);
//        model.addAttribute("name", username);
//
//        return "account/account";
//    }

    @GetMapping("/account")
    public String accountUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        String email;
        String role;

        // Проверьте, что authentication содержит OAuth2User
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            username = oauthUser.getAttribute("name");
            email = oauthUser.getAttribute("email");
            role = "USER"; // Вы можете определить роль как "USER" по умолчанию или на основе другой логики
        } else {
            // Если не OAuth2User, используйте имя пользователя из контекста безопасности
            username = authentication.getName();
            email = "unknown"; // Или другой способ получения email
            role = "USER"; // Или другая логика для роли
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        // Создание объекта UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        userDTO.setRole(isAdmin ? "ADMIN" : role);
        userDTO.setBlocked(false); // Установите значение по умолчанию или получите его из базы данных

        model.addAttribute("user", userDTO);
        model.addAttribute("name", username);

        return "account/account";
    }







    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}

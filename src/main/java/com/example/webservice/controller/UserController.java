//package com.example.webservice.controller;
//
//import com.example.webservice.dto.UserDTO;
//import com.example.webservice.repository.UserFeignClient;
//import feign.FeignException;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@AllArgsConstructor
//public class UserController {
//
//    private final UserFeignClient userFeignClient;
//
//    @GetMapping("/login")
//    public String loginForm(Model model) {
//        model.addAttribute("userDto", new UserDTO());
//        return "user/Login";
//    }
//    @PostMapping("/login")
//    public String loginUser(@RequestParam String username, @RequestParam String password, Model model) {
//        try {
//            ResponseEntity<String> response = userFeignClient.login(username, password);
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//                model.addAttribute("message", "Login successful");
//                return "redirect:/";
//            } else {
//                model.addAttribute("error", "Invalid credentials");
//                return "redirect:/";
//            }
//        } catch (FeignException e) {
//            model.addAttribute("error", "Login failed: " + e.getMessage());
//            return "redirect:/";
//        }
//    }
//
//
//    @GetMapping("/register")
//    public String registerFrom(Model model) {
//        model.addAttribute("userDto", new UserDTO());
//        return "user/register";
//    }
//
//    @GetMapping("/users")
//    public String getAllUsers(Model model) {
//        List<UserDTO> users = userFeignClient.getAllUsers();
//        model.addAttribute("users", users);
//        return "users"; // Имя HTML-шаблона для отображения списка пользователей
//    }
//
//    @PostMapping("/users/register")
//    public String registerUser(@ModelAttribute UserDTO userDTO) {
//        userFeignClient.registerUser(userDTO);
//        return "redirect:/";
//    }
//
//    @PostMapping("/users/change-password")
//    public String changePassword(
//            @RequestParam String username,
//            @RequestParam String currentPassword,
//            @RequestParam String newPassword) {
//        userFeignClient.changePassword(username, currentPassword, newPassword);
//        return "redirect:/users";
//    }
//
//    @PostMapping("/users/block/{userId}")
//    public String blockUser(@PathVariable Long userId) {
//        userFeignClient.blockUser(userId);
//        return "redirect:/users";
//    }
//
//    @PostMapping("/users/unblock/{userId}")
//    public String unblockUser(@PathVariable Long userId) {
//        userFeignClient.unblockUser(userId);
//        return "redirect:/users";
//    }
//
//    @GetMapping("/users/is-blocked")
//    public String isUserBlocked(@RequestParam String username, Model model) {
//        Boolean isBlocked = userFeignClient.isBlocked(username);
//        model.addAttribute("isBlocked", isBlocked);
//        return "user-status"; // Имя HTML-шаблона для отображения статуса пользователя
//    }
//}

package com.example.webservice.controller;

import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.UserFeignClient;
import com.example.webservice.service.UserService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserFeignClient userFeignClient;
    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "user/Login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<UserDTO> response = userFeignClient.login(username, password);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                UserDTO userDTO = response.getBody();

                // Проверка, заблокирован ли пользователь
                if (userDTO.isBlocked()) {
                    return "redirect:/blocked"; // Редирект на страницу с сообщением о блокировке
                }

                // Логика успешного входа
                return "redirect:/"; // Переход на главную страницу или другую страницу после успешного входа
            } else {
                redirectAttributes.addFlashAttribute("error", "Неправильный логин или пароль");
                return "redirect:/login"; // Редирект на страницу входа с ошибкой
            }
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка входа: " + e.getMessage());
            return "redirect:/login"; // Редирект на страницу входа с ошибкой
        }
    }
    @GetMapping("/blocked")
    public String blocked(){
        return "user/Blocked";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserDTO userDTO) {
        userFeignClient.registerUser(userDTO);
        return "redirect:/";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserDTO> users = userFeignClient.getAllUsers();
        model.addAttribute("users", users);
        return "users"; // Имя HTML-шаблона для отображения списка пользователей
    }


    @PostMapping("/users/block/{userId}")
    public String blockUser(@PathVariable Long userId) {
        userFeignClient.blockUser(userId);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/unblock/{userId}")
    public String unblockUser(@PathVariable Long userId) {
        userFeignClient.unblockUser(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/is-blocked")
    public String isUserBlocked(@RequestParam String username, Model model) {
        Boolean isBlocked = userFeignClient.isBlocked(username);
        model.addAttribute("isBlocked", isBlocked);
        return "user-status"; // Имя HTML-шаблона для отображения статуса пользователя
    }
    @PostMapping("/users/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
       userFeignClient.deleteUserById(id);
        return "redirect:/admin/users";

    }

}

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
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserFeignClient userFeignClient;




    @GetMapping("/ac")
    public String accountUser(Model model, Authentication authentication) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String username = oauthUser.getAttribute("name"); // Используйте правильный атрибут, соответствующий вашему OAuth2 провайдеру
        System.out.println("Retrieved username: " + username);
        try {
            UserDTO userDTO = userFeignClient.getUserByUsername(username);
            if (userDTO == null) {
                return "redirect:/error";
            }
            model.addAttribute("user", userDTO);
            return "user/ac";
        } catch (Exception e) {
            log.error("Error retrieving user details", e);
            return "redirect:/error";
        }
    }


    @GetMapping("/user/{username}")
    public String testGetUserByUsername(@PathVariable String username, Model model) {
        try {
            // Использование FeignClient для получения информации о пользователе
            UserDTO userDTO = userFeignClient.getUserByUsername(username);

            if (userDTO == null) {
                log.warn("User not found for username: {}", username);
                model.addAttribute("error", "User not found");
                return "error"; // Назначьте соответствующую страницу ошибки
            }

            // Добавление пользователя в модель для отображения на странице
            model.addAttribute("user", userDTO);
            return "user/userDetails"; // Назначьте страницу, где будет отображаться информация о пользователе
        } catch (Exception e) {
            log.error("Error occurred while fetching user details", e);
            model.addAttribute("error", "An error occurred");
            return "error"; // Назначьте соответствующую страницу ошибки
        }
    }

@PostMapping("/register")
public RedirectView registerUser(@ModelAttribute UserDTO userDTO) {
    try {
        userFeignClient.registerUser(userDTO);
        return new RedirectView("/");
    } catch (Exception e) {
        log.error("Error registering user", e);
        return new RedirectView("/error"); // Перенаправление на страницу ошибки, если нужно
    }
}
    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            ResponseEntity<UserDTO> response = userFeignClient.login(username, password);

            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                UserDTO userDTO = response.getBody();

                if (userDTO.isBlocked()) {
                    return "redirect:/blocked";
                }

                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("error", "Неправильный логин или пароль");
                return "redirect:/login";
            }
        } catch (FeignException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка входа: " + e.getMessage());
            return "redirect:/login";
        }
    }




    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "user/Login";
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

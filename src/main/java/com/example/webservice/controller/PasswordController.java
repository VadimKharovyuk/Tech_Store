package com.example.webservice.controller;
import com.example.webservice.repository.UserFeignClient;
import com.example.webservice.service.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PasswordController {

    private final UserFeignClient userFeignClient;

    private  final PasswordGenerator passwordGenerator;



    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "account/change-password"; // Имя HTML-шаблона для формы
    }

    @PostMapping("/users/change-password")
    public String changePassword(
            @RequestParam String username,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        userFeignClient.changePassword(username, currentPassword, newPassword);
        return "redirect:/account";  }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        try {
            // Генерация нового пароля
            String newPassword = passwordGenerator.generateNewPassword(12); // длиной 12 символов

            // Отправка нового пароля на электронную почту
            ResponseEntity<String> response = userFeignClient.sendEmailPassword(email, newPassword);

            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok("New password sent");
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Failed to send new password");
            }
        } catch (Exception e) {
            // Логирование исключения и обработка ошибки
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error occurred while processing request");
        }
    }

    @GetMapping("/resetPassword")
    public String resetPasswordForm(){
        return "account/reset-password";
    }
}

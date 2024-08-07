package com.example.webservice.controller;
import com.example.webservice.repository.UserFeignClient;
import com.example.webservice.service.PasswordGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PasswordController {

    private final UserFeignClient userFeignClient;


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
        return "redirect:/account";
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        try {
            // Генерация нового пароля
            String newPassword = generateNewPassword(10); // Длина пароля, например, 10 символов

            // Отправка нового пароля через FeignClient
            ResponseEntity<String> response = userFeignClient.sendEmailPassword(email, newPassword);
            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok("Пароль сброшен и отправлен на почту.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось отправить пароль на почту.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при сбросе пароля.");
        }
    }


    private String generateNewPassword(int length) {

        String characters = "ABCD1234567890";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (characters.length() * Math.random());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }





    @GetMapping("/resetPassword")
    public String resetPasswordForm(){
        return "account/reset-password";
    }
}

package com.example.security.controller;

import com.example.security.dto.UserDTO;
import com.example.security.repository.EmailFeignClient;
import com.example.security.service.PasswordGenerator;
import com.example.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/password")
public class PasswordResetController {

    private final PasswordGenerator passwordGenerator;
    private final UserService userService;
    private final EmailFeignClient emailFeignClient;
    private final PasswordEncoder passwordEncoder;



    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam String email) {
        Optional<UserDTO> userOpt = userService.findUserByEmail(email);
        if (userOpt.isPresent()) {
            UserDTO user = userOpt.get();
            String newPassword = passwordGenerator.generateNewPassword(6); // длина пароля, например, 10 символов

            // Хэшируйте пароль перед сохранением
            String hashedPassword = hashPassword(newPassword);

            user.setPassword(hashedPassword);
            userService.save(user);

            // Вызов метода через FeignClient
            ResponseEntity<Void> response = emailFeignClient.sendEmailPassword(email, newPassword);
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok("Пароль сброшен и отправлен на почту.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось отправить пароль на почту.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь не найден.");
        }
    }


    private String hashPassword(String password) {
        // Используйте BCryptPasswordEncoder для хэширования пароля
        return passwordEncoder.encode(password); // Возвращает хэшированный пароль
    }

}

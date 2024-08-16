package com.example.emailservicerest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;


    @PostMapping("/password")
    public ResponseEntity<String> sendEmailPassword(@RequestParam("email") String email, @RequestParam("pass") String pass) {
        emailService.sendPasswordReset(email, pass);
        return ResponseEntity.ok().build(); // Возвращает статус 200 без тела ответа
    }


    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendRegistrationEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getBody());
        return ResponseEntity.ok("Email sent");
    }
}

package com.example.emailservicerest.controller;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;


    public void sendPasswordReset(String recipientEmail, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Сброс пароля");
        message.setText("Ваш новый пароль: " + newPassword);

        emailSender.send(message);
    }


    public void sendRegistrationEmail(String to, String username, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to Our Store!");
        message.setText("Dear " + username + ",\n\nThank you for registering at our store. We are excited to have you with us!\n\nBest regards,\nThe Store Team");

        emailSender.send(message);
    }


}

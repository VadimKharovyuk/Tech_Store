package com.example.webservice.config;

import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.UserFeignClient;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserFeignClient userFeignClient;

    public OAuth2LoginSuccessHandler(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");

        // Проверка наличия пользователя в базе данных
        if (!userExists(email)) {
            // Создание нового пользователя
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(email);
            userDTO.setUsername(oauthUser.getAttribute("name")); // Здесь можно использовать "username" или "name" в зависимости от вашего проекта
            userDTO.setRole("USER"); // Присваивание роли пользователю
            userDTO.setBlocked(false); // По умолчанию пользователь не заблокирован

            // Регистрация пользователя через FeignClient
            userFeignClient.registerUser(userDTO);
        }

        response.sendRedirect("/");
    }

    private boolean userExists(String email) {
        try {
            // Попытка получить пользователя по email через FeignClient
            UserDTO existingUser = userFeignClient.getUserByEmail(email);
            return existingUser != null;
        } catch (FeignException e) {

            return false;
        }
}
}

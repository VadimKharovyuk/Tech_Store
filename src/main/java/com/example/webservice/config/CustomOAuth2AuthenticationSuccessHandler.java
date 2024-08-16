package com.example.webservice.config;

import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.UserFeignClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserFeignClient userFeignClient;

    public CustomOAuth2AuthenticationSuccessHandler(UserFeignClient userFeignClient) {
        this.userFeignClient = userFeignClient;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");

        UserDTO user = userFeignClient.getUserByUsername(email);
        if (user == null) {
            user = new UserDTO();
            user.setEmail(email);
            user.setUsername((String) attributes.get("name"));
            userFeignClient.registerUser(user);
        }

        response.sendRedirect("/products");
    }
}

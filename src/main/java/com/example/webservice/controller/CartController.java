package com.example.webservice.controller;

import com.example.webservice.dto.CartDto;
import com.example.webservice.dto.CartItemDto;
import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.CartFeignClient;
import com.example.webservice.repository.UserFeignClient;
import com.example.webservice.service.CardService;
import com.example.webservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartFeignClient cartFeignClient;
    private final UserFeignClient userFeignClient;
    private final CardService cardService;



//    @PostMapping("/add-to-cart")
//    public String addProductToCart(@ModelAttribute CartItemDto cartItemDto, RedirectAttributes redirectAttributes) {
//        try {
//            // Логирование данных запроса
//            log.info("Adding product to cart: {}", cartItemDto);
//
//            // Получение текущего аутентифицированного пользователя
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username = authentication.getName(); // Получаем имя пользователя
//
//            // Получение информации о пользователе
//            UserDTO user = userFeignClient.getUserByUsername(username);
//            if (user == null) {
//                throw new Exception("User not found");
//            }
//
//            // Обновляем DTO с ID пользователя
//            cartItemDto.setUserId(user.getId());
//
//            // Используем сервис для добавления товара в корзину
//            cardService.addProductToCart(cartItemDto);
//
//            // Устанавливаем сообщение об успешном добавлении
//            redirectAttributes.addFlashAttribute("message", "Product added to cart successfully!");
//            return "redirect:/cart"; // Перенаправляем на страницу корзины
//        } catch (Exception e) {
//            log.error("Failed to add product to cart", e); // Логирование ошибки
//            redirectAttributes.addFlashAttribute("error", "Failed to add product to cart.");
//            return "redirect:/"; // Перенаправляем на главную страницу
//        }
//    }
@PostMapping("/add-to-cart")
public String addProductToCart(@ModelAttribute CartItemDto cartItemDto, RedirectAttributes redirectAttributes) {
    try {
        // Логирование данных запроса
        log.info("Adding product to cart: {}", cartItemDto);

        // Получение текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        Long userId;

        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            username = oauthUser.getAttribute("name");
        } else {
            username = authentication.getName();
        }

        // Получение информации о пользователе
        UserDTO user = userFeignClient.getUserByUsername(username);
        if (user == null) {
            throw new Exception("User not found");
        }

        // Обновляем DTO с ID пользователя
        cartItemDto.setUserId(user.getId());

        // Используем сервис для добавления товара в корзину
        cardService.addProductToCart(cartItemDto);

        // Устанавливаем сообщение об успешном добавлении
        redirectAttributes.addFlashAttribute("message", "Product added to cart successfully!");
        return "redirect:/cart"; // Перенаправляем на страницу корзины
    } catch (Exception e) {
        log.error("Failed to add product to cart", e); // Логирование ошибки
        redirectAttributes.addFlashAttribute("error", "Failed to add product to cart.");
        return "redirect:/"; // Перенаправляем на главную страницу
    }
}



//    @GetMapping
//    public String getCart(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        // Получение информации о пользователе
//        UserDTO user = userFeignClient.getUserByUsername(username);
//        Long userId = user.getId(); // Получаем userId из объекта UserDTO
//
//        ResponseEntity<CartDto> response = cartFeignClient.getCart(userId);
//        if (response.getStatusCode() == HttpStatus.OK) {
//            CartDto cartDto = response.getBody();
//            if (cartDto != null && cartDto.getItems() != null && !cartDto.getItems().isEmpty()) {
//                model.addAttribute("cart", cartDto);
//                return "cart/cart"; // имя HTML-шаблона
//            } else {
//                model.addAttribute("message", "Ваша корзина пуста.");
//                return "cart/cart"; // имя HTML-шаблона с пустой корзиной
//            }
//        } else {
//            model.addAttribute("error", "Failed to retrieve cart");
//            return "error"; // имя HTML-шаблона для ошибки
//        }
//    }

@GetMapping
public String getCart(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username;
    Long userId;

    if (authentication.getPrincipal() instanceof OAuth2User) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        username = oauthUser.getAttribute("name");
    } else {
        username = authentication.getName();
    }

    // Получение информации о пользователе
    UserDTO user = userFeignClient.getUserByUsername(username);
    if (user == null) {
        model.addAttribute("error", "User not found");
        return "error"; // имя HTML-шаблона для ошибки
    }

    userId = user.getId(); // Получаем userId из объекта UserDTO

    ResponseEntity<CartDto> response = cartFeignClient.getCart(userId);
    if (response.getStatusCode() == HttpStatus.OK) {
        CartDto cartDto = response.getBody();
        if (cartDto != null && cartDto.getItems() != null && !cartDto.getItems().isEmpty()) {
            model.addAttribute("cart", cartDto);
            return "cart/cart"; // имя HTML-шаблона
        } else {
            model.addAttribute("message", "Ваша корзина пуста.");
            return "cart/cart"; // имя HTML-шаблона с пустой корзиной
        }
    } else {
        model.addAttribute("error", "Failed to retrieve cart");
        return "error"; // имя HTML-шаблона для ошибки
    }
}


//    @PostMapping("/remove-from-cart")
//    public String removeProductFromCart(@RequestParam Long itemId, RedirectAttributes redirectAttributes) {
//        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username = authentication.getName();
//            UserDTO user = userFeignClient.getUserByUsername(username);
//            Long userId = user.getId(); // Получаем userId из объекта UserDTO
//
//            cartFeignClient.removeItemFromCart(itemId, userId);
//
//            redirectAttributes.addFlashAttribute("message", "Product removed from cart successfully!");
//            return "redirect:/cart"; // Перенаправляем на страницу корзины
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Failed to remove product from cart.");
//            return "redirect:/cart"; // Перенаправляем на страницу корзины с ошибкой
//        }
//    }
@PostMapping("/remove-from-cart")
public String removeProductFromCart(@RequestParam Long itemId, RedirectAttributes redirectAttributes) {
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username;
        Long userId;

        // Проверка типа Authentication и извлечение имени пользователя
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
            username = oauthUser.getAttribute("name");
        } else {
            username = authentication.getName();
        }

        // Получение информации о пользователе
        UserDTO user = userFeignClient.getUserByUsername(username);
        if (user == null) {
            throw new Exception("User not found");
        }

        userId = user.getId(); // Получаем userId из объекта UserDTO

        // Вызов метода для удаления товара из корзины
        cartFeignClient.removeItemFromCart(itemId, userId);

        redirectAttributes.addFlashAttribute("message", "Product removed from cart successfully!");
        return "redirect:/cart"; // Перенаправляем на страницу корзины
    } catch (Exception e) {
        log.error("Failed to remove product from cart", e); // Логирование ошибки
        redirectAttributes.addFlashAttribute("error", "Failed to remove product from cart.");
        return "redirect:/cart"; // Перенаправляем на страницу корзины с ошибкой
    }
}


}


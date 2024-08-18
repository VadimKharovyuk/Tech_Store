
package com.example.webservice.controller;

import com.example.webservice.dto.*;
import com.example.webservice.repository.CartFeignClient;
import com.example.webservice.repository.DeliveryClient;
import com.example.webservice.repository.UserFeignClient;
import com.example.webservice.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryClient deliveryClient;
    private final UserFeignClient userFeignClient;
    private final CartFeignClient cartFeignClient;
    private final DeliveryService deliveryService;

//    @PostMapping("/deliveries")
//    public String createDelivery(@ModelAttribute DeliveryDTO deliveryRequest, Model model) {
//        // Получаем информацию о текущем пользователе
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        // Получаем информацию о пользователе
//        UserDTO user = userFeignClient.getUserByUsername(username);
//        Long userId = user.getId(); // Получаем userId из объекта UserDTO
//
//        // Устанавливаем userId в DeliveryDTO
//        deliveryRequest.setUserId(userId);
//
//        // Вычисляем общую сумму
//        BigDecimal totalAmount = deliveryService.computeTotalAmount(deliveryRequest.getItems());
//        deliveryRequest.setTotalAmount(totalAmount);
//
//
//        // Создание доставки через клиент
//        DeliveryDTO createdDelivery = deliveryClient.createDelivery(deliveryRequest);
//
//        // Добавление созданной доставки в модель
//        model.addAttribute("delivery", createdDelivery);
//
//        // Переход на страницу с деталями доставки
//        return "redirect:/deliveries";
//    }
@PostMapping("/deliveries")
public String createDelivery(@ModelAttribute DeliveryDTO deliveryRequest, Model model) {
    // Получаем информацию о текущем пользователе
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username;

    if (authentication.getPrincipal() instanceof OAuth2User) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        username = oauthUser.getAttribute("name"); // Или используйте нужный атрибут, например, "email", "sub" и т.д.
    } else {
        username = authentication.getName();
    }

    // Получаем информацию о пользователе
    UserDTO user = userFeignClient.getUserByUsername(username);
    if (user == null) {
        model.addAttribute("error", "User not found");
        return "error"; // имя HTML-шаблона для ошибки
    }

    Long userId = user.getId(); // Получаем userId из объекта UserDTO

    // Устанавливаем userId в DeliveryDTO
    deliveryRequest.setUserId(userId);

    // Вычисляем общую сумму
    BigDecimal totalAmount = deliveryService.computeTotalAmount(deliveryRequest.getItems());
    deliveryRequest.setTotalAmount(totalAmount);

    // Создание доставки через клиент
    DeliveryDTO createdDelivery = deliveryClient.createDelivery(deliveryRequest);

    // Добавление созданной доставки в модель
    model.addAttribute("delivery", createdDelivery);

    // Переход на страницу с деталями доставки
    return "redirect:/deliveries";
}




    @GetMapping("/deliveries-list")
    public String listDeliveries(Model model) {
        List<DeliveryDTO> deliveries = deliveryClient.listDeliveries();
        model.addAttribute("deliveries", deliveries);
        return "deliveries/list-all-deliveries"; // Имя шаблона Thymeleaf для отображения списка доставок
    }

//    @GetMapping("/deliveries")
//    public String listDeliveriesUser(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        // Получение информации о пользователе
//        UserDTO user = userFeignClient.getUserByUsername(username);
//        Long userId = user.getId(); // Получаем userId из объекта UserDTO
//
//        // Получение списка доставок для пользователя
//        List<DeliveryDTO> deliveries = deliveryClient.listDeliveriesByUserId(userId);
//
//        BigDecimal totalAmount = deliveries.stream()
//                .map(DeliveryDTO::getTotalAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        // Добавление списка доставок в модель
//        model.addAttribute("deliveries", deliveries);
//        model.addAttribute("totalAmount", totalAmount);
//
//        return "deliveries/list-deliveries"; // Имя шаблона Thymeleaf для отображения списка доставок
//    }
@GetMapping("/deliveries")
public String listDeliveriesUser(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username;

    if (authentication.getPrincipal() instanceof OAuth2User) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        username = oauthUser.getAttribute("name"); // Или используйте нужный атрибут, например, "email", "sub" и т.д.
    } else {
        username = authentication.getName();
    }

    // Получение информации о пользователе
    UserDTO user = userFeignClient.getUserByUsername(username);
    if (user == null) {
        model.addAttribute("error", "User not found");
        return "error"; // имя HTML-шаблона для ошибки
    }

    Long userId = user.getId(); // Получаем userId из объекта UserDTO

    // Получение списка доставок для пользователя
    List<DeliveryDTO> deliveries = deliveryClient.listDeliveriesByUserId(userId);

    BigDecimal totalAmount = deliveries.stream()
            .map(DeliveryDTO::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    // Добавление списка доставок в модель
    model.addAttribute("deliveries", deliveries);
    model.addAttribute("totalAmount", totalAmount);

    return "deliveries/list-deliveries"; // Имя шаблона Thymeleaf для отображения списка доставок
}


//    @GetMapping("/create")
//    public String showCreateDeliveryForm(Model model) {
//        // Получаем ID текущего пользователя (возможно, из сессии или контекста безопасности)
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        // Получение информации о пользователе
//        UserDTO user = userFeignClient.getUserByUsername(username);
//        Long userId = user.getId(); // Получаем userId из объекта UserDTO
//
//        // Получаем корзину пользователя
//        ResponseEntity<CartDto> responseEntity = cartFeignClient.getCart(userId);
//        CartDto cartDto = responseEntity.getBody(); // Извлекаем тело из ResponseEntity
//        List<CartItemDto> cartItems = cartDto != null ? cartDto.getItems() : new ArrayList<>(); // Получаем список товаров из CartDto
//
//        // Создаем объект DeliveryDTO и добавляем в него товары
//        DeliveryDTO delivery = new DeliveryDTO();
//        delivery.setItems(cartItems);
//        delivery.setUserId(userId);
//
//        // Устанавливаем общую сумму
//        BigDecimal totalAmount = deliveryService.computeTotalAmount(cartItems); // Используем метод из сервиса
//        delivery.setTotalAmount(totalAmount);
//
//
//        // Добавляем DeliveryDTO в модель
//        model.addAttribute("delivery", delivery);
//        model.addAttribute("cities", Arrays.asList(City.values())); // Передайте список городов
//        model.addAttribute("statuses", DeliveryStatus.values());
//
//        return "deliveries/create-delivery";
//    }
@GetMapping("/create")
public String showCreateDeliveryForm(Model model) {
    // Получаем ID текущего пользователя
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username;

    if (authentication.getPrincipal() instanceof OAuth2User) {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        username = oauthUser.getAttribute("name"); // Или используйте нужный атрибут, например, "email", "sub" и т.д.
    } else {
        username = authentication.getName();
    }

    // Получение информации о пользователе
    UserDTO user = userFeignClient.getUserByUsername(username);
    if (user == null) {
        model.addAttribute("error", "User not found");
        return "error"; // имя HTML-шаблона для ошибки
    }

    Long userId = user.getId(); // Получаем userId из объекта UserDTO

    // Получаем корзину пользователя
    ResponseEntity<CartDto> responseEntity = cartFeignClient.getCart(userId);
    CartDto cartDto = responseEntity.getBody(); // Извлекаем тело из ResponseEntity
    List<CartItemDto> cartItems = cartDto != null ? cartDto.getItems() : new ArrayList<>(); // Получаем список товаров из CartDto

    // Создаем объект DeliveryDTO и добавляем в него товары
    DeliveryDTO delivery = new DeliveryDTO();
    delivery.setItems(cartItems);
    delivery.setUserId(userId);

    // Устанавливаем общую сумму
    BigDecimal totalAmount = deliveryService.computeTotalAmount(cartItems); // Используем метод из сервиса
    delivery.setTotalAmount(totalAmount);

    // Добавляем DeliveryDTO в модель
    model.addAttribute("delivery", delivery);
    model.addAttribute("cities", Arrays.asList(City.values())); // Передайте список городов
    model.addAttribute("statuses", DeliveryStatus.values());

    return "deliveries/create-delivery"; // Имя шаблона Thymeleaf для отображения формы создания доставки
}



}

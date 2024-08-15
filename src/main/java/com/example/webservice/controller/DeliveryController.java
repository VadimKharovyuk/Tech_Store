//package com.example.webservice.controller;
//
//import com.example.webservice.dto.CartDto;
//import com.example.webservice.dto.CartItemDto;
//import com.example.webservice.dto.DeliveryDTO;
//import com.example.webservice.dto.UserDTO;
//import com.example.webservice.repository.CartFeignClient;
//import com.example.webservice.repository.DeliveryClient;
//import com.example.webservice.repository.UserFeignClient;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//@AllArgsConstructor
//public class DeliveryController {
//
//    private final DeliveryClient deliveryClient;
//    private final UserFeignClient userFeignClient;
//    private final CartFeignClient cartFeignClient;
//
//
//    @GetMapping("/deliveries")
//    public String listDeliveries(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        // Получение информации о пользователе
//        UserDTO user = userFeignClient.getUserByUsername(username);
//
//        if (user == null) {
//            model.addAttribute("error", "User not found");
//            return "error";
//        }
//
//        Long userId = user.getId(); // Получаем userId из объекта UserDTO
//        List<DeliveryDTO> deliveries = deliveryClient.listDeliveries()
//                .stream()
//                .filter(delivery -> delivery.getUserId().equals(userId))
//                .collect(Collectors.toList());
//
//        model.addAttribute("deliveries", deliveries);
//        return "deliveries/list-deliveries";
//    }
//
//    @PostMapping("/deliveries")
//    public String createDelivery(@ModelAttribute DeliveryDTO deliveryDTO) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        UserDTO user = userFeignClient.getUserByUsername(username);
//        Long userId = user.getId(); // Получаем userId из объекта UserDTO
//
//        if (user != null) {
//            deliveryDTO.setUserId(user.getId());
//            deliveryClient.createDelivery(deliveryDTO);
//        }
//
//        return "redirect:/deliveries";
//    }
//
//    @GetMapping("/deliveries/new")
//    public String newDeliveryForm(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        // Получение информации о пользователе
//        UserDTO user = userFeignClient.getUserByUsername(username);
//
//
//
//        model.addAttribute("deliveryRequest", new DeliveryDTO());
//        return "deliveries/create-delivery";
//    }
//
//
//}

package com.example.webservice.controller;

import com.example.webservice.dto.CartDto;
import com.example.webservice.dto.CartItemDto;
import com.example.webservice.dto.DeliveryDTO;
import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.CartFeignClient;
import com.example.webservice.repository.DeliveryClient;
import com.example.webservice.repository.UserFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryClient deliveryClient;
    private final UserFeignClient userFeignClient;
    private final CartFeignClient cartFeignClient;


@PostMapping("/deliveries")
public String createDelivery(@ModelAttribute DeliveryDTO deliveryRequest, Model model) {
    // Создание доставки через клиент
    DeliveryDTO createdDelivery = deliveryClient.createDelivery(deliveryRequest);

    // Добавление созданной доставки в модель
    model.addAttribute("delivery", createdDelivery);

    // Переход на страницу с деталями доставки
    return "deliveries/deliveryDetails"; // Имя шаблона Thymeleaf для отображения деталей доставки
}


    @GetMapping("/deliveries-list")
    public String listDeliveries(Model model) {
        List<DeliveryDTO> deliveries = deliveryClient.listDeliveries();
        model.addAttribute("deliveries", deliveries);
        return "deliveries/list-all-deliveries"; // Имя шаблона Thymeleaf для отображения списка доставок
    }

    @GetMapping("/deliveries")
    public String listDeliveries(@RequestParam("username") String username, Model model) {
        UserDTO user = userFeignClient.getUserByUsername(username);
        if (user != null) {
            List<DeliveryDTO> deliveries = deliveryClient.listDeliveriesByUserId(user.getId());
            model.addAttribute("deliveries", deliveries);
        }
        return "deliveries/list-deliveries"; // Имя шаблона Thymeleaf для отображения списка доставок
    }

@GetMapping("/create")
public String showCreateDeliveryForm(Model model) {
    // Получаем ID текущего пользователя (возможно, из сессии или контекста безопасности)
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    // Получение информации о пользователе
    UserDTO user = userFeignClient.getUserByUsername(username);
    Long userId = user.getId(); // Получаем userId из объекта UserDTO

    // Получаем корзину пользователя
    ResponseEntity<CartDto> responseEntity = cartFeignClient.getCart(userId);
    CartDto cartDto = responseEntity.getBody(); // Извлекаем тело из ResponseEntity
    List<CartItemDto> cartItems = cartDto != null ? cartDto.getItems() : new ArrayList<>(); // Получаем список товаров из CartDto

    // Создаем объект DeliveryDTO и добавляем в него товары
    DeliveryDTO delivery = new DeliveryDTO();
    delivery.setItems(cartItems);
    delivery.setUserId(userId);

    // Добавляем DeliveryDTO в модель
    model.addAttribute("delivery", delivery);

    return "deliveries/create-delivery";
}



}
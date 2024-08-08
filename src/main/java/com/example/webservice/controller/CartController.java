//package com.example.webservice.controller;
//
//import com.example.webservice.dto.CartDto;
//import com.example.webservice.dto.UserDTO;
//import com.example.webservice.repository.CartFeignClient;
//import com.example.webservice.repository.UserFeignClient;
//import com.example.webservice.service.ProductService;
//import com.example.webservice.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/cart")
//public class CartController {
//
//    private final CartFeignClient cartFeignClient;
//    private final ProductService productService;
//    private final UserFeignClient userFeignClient;
//
//    @PostMapping("/add-to-cart")
//    public ResponseEntity<String> addProductToCart(
//            @RequestParam("userId") Long userId,
//            @RequestParam("productId") Long productId,
//            @RequestParam("productName") String productName,
//            @RequestParam("quantity") int quantity) {
//
//        try {
//            // Вызов метода сервиса для добавления товара в корзину
//            productService.addProductToCart(userId, productId, productName, quantity);
//            return new ResponseEntity<>("Product added to cart successfully.", HttpStatus.OK);
//        } catch (Exception e) {
//            // Обработка ошибок и возврат сообщения об ошибке
//            return new ResponseEntity<>("Failed to add product to cart: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/cart")
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
//            if (cartDto != null) {
//                model.addAttribute("cart", cartDto);
//                return "cart/cart"; // имя HTML-шаблона
//            } else {
//                model.addAttribute("error", "Cart not found");
//                return "error"; // имя HTML-шаблона для ошибки
//            }
//        } else {
//            model.addAttribute("error", "Failed to retrieve cart");
//            return "error"; // имя HTML-шаблона для ошибки
//        }
//    }
//
//}
package com.example.webservice.controller;

import com.example.webservice.dto.CartDto;
import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.CartFeignClient;
import com.example.webservice.repository.UserFeignClient;
import com.example.webservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartFeignClient cartFeignClient;
    private final ProductService productService;
    private final UserFeignClient userFeignClient;

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addProductToCart(
            @RequestParam("userId") Long userId,
            @RequestParam("productId") Long productId,
            @RequestParam("productName") String productName,
            @RequestParam("quantity") int quantity) {

        try {
            // Вызов метода сервиса для добавления товара в корзину
            productService.addProductToCart(userId, productId, productName, quantity);
            return new ResponseEntity<>("Product added to cart successfully.", HttpStatus.OK);
        } catch (Exception e) {
            // Обработка ошибок и возврат сообщения об ошибке
            return new ResponseEntity<>("Failed to add product to cart: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping
    public String getCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Получение информации о пользователе
        UserDTO user = userFeignClient.getUserByUsername(username);
        Long userId = user.getId(); // Получаем userId из объекта UserDTO

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
}

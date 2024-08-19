package com.example.cartservis.controller;

import com.example.cartservis.dto.CartDto;
import com.example.cartservis.dto.CartItemDto;
import com.example.cartservis.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    // Получить корзину по userId
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable("userId") Long userId) {
        try {
            CartDto cartDto = cartService.getCart(userId);
            if (cartDto == null || cartDto.getItems().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Корзина не найдена
            }
            return new ResponseEntity<>(cartDto, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error retrieving cart for userId {}: {}", userId, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Ошибка сервера
        }
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addItemToCart(@RequestBody CartItemDto cartItemDto) {
        try {
            cartService.addItemToCart(cartItemDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid CartItemDto: {}", cartItemDto, e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Неверный запрос
        } catch (Exception e) {
            logger.error("Error adding item to cart: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Ошибка сервера
        }
    }
    @PostMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long itemId, @RequestParam Long userId) {
        try {
            cartService.removeItemFromCart(userId, itemId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

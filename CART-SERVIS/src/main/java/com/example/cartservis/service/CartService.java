package com.example.cartservis.service;

import com.example.cartservis.dto.CartDto;
import com.example.cartservis.dto.CartItemDto;
import com.example.cartservis.model.Cart;
import com.example.cartservis.model.CartItem;
import com.example.cartservis.repository.CartItemRepository;
import com.example.cartservis.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    logger.info("Cart not found for user id {}", userId);
                    return new Cart(); // Можно рассмотреть вариант создания новой корзины или другой обработки
                });
        return convertToDto(cart);
    }

    public void addItemToCart(CartItemDto cartItemDto) {
        // Найти или создать корзину
        Cart cart = cartRepository.findByUserId(cartItemDto.getUserId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(cartItemDto.getUserId());
                    return cartRepository.save(newCart);
                });

        // Создание CartItem из CartItemDto
        CartItem cartItem = new CartItem();
        cartItem.setProductId(cartItemDto.getProductId());
        cartItem.setProductName(cartItemDto.getProductName());
        cartItem.setProductPrice(cartItemDto.getPrice()); // Устанавливаем значение productPrice
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setCart(cart);

        // Сохранение CartItem в базе данных
        cartItemRepository.save(cartItem);

        // Обновление корзины с новым элементом
        cart.getItems().add(cartItem);
        cartRepository.save(cart);
    }

    // Convert Cart to CartDto
    private CartDto convertToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUserId());
        cartDto.setItems(cart.getItems().stream()
                .map(item -> {
                    CartItemDto itemDto = new CartItemDto();
                    itemDto.setId(item.getId());
                    itemDto.setProductId(item.getProductId());
                    itemDto.setProductName(item.getProductName());
                    itemDto.setQuantity(item.getQuantity());
                    itemDto.setPrice(item.getProductPrice()); // Добавлено поле
                    return itemDto;
                })
                .collect(Collectors.toSet()));
        return cartDto;
    }
    public void removeItemFromCart(Long userId, Long itemId) {
        // Найти корзину пользователя
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user id " + userId));

        // Найти элемент корзины по ID
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id " + itemId));

        // Удалить элемент корзины из корзины
        cart.getItems().remove(cartItem);

        // Удалить элемент корзины из базы данных
        cartItemRepository.delete(cartItem);

        // Сохранить обновленную корзину
        cartRepository.save(cart);
    }
}



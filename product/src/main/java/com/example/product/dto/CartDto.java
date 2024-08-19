package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private Long userId; // Поле userId
    private Set<CartItemDto> items; // Поле items
}
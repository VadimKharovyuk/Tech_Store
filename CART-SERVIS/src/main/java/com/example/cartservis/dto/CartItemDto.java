package com.example.cartservis.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private Long userId; // Поле userId, если нужно
    private BigDecimal price; // Поле price

}

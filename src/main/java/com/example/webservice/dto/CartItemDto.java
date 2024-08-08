package com.example.webservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class CartItemDto {
        private Long productId;
        private String productName;
        private BigDecimal productPrice; // Возможно, вам потребуется добавить цену продукта
        private Integer quantity;
        private Long userId;
    }

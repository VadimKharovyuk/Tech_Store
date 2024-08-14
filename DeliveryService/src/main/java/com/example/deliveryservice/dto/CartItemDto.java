package com.example.deliveryservice.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Embeddable
public class CartItemDto {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private Long userId;
}

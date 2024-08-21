package com.example.webservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private Long userId;


}

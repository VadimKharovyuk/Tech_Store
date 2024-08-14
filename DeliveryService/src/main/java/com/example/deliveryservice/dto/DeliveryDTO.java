package com.example.deliveryservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeliveryDTO {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String city; // Город как строка
    private Long userId;
    private List<CartItemDto> items;
}

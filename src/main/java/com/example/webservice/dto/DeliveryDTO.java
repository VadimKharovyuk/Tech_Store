package com.example.webservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class DeliveryDTO {
    private Long id;

    private String fullName;
    private String phoneNumber;
    private String address;
    private City city;
    private DeliveryStatus status;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private Long userId;
}

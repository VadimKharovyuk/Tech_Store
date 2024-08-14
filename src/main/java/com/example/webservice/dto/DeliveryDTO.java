package com.example.webservice.dto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class DeliveryDTO {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String city;
    private String status;
    private List<CartItemDto> items;
    private BigDecimal totalAmount;
    private Long userId;
}

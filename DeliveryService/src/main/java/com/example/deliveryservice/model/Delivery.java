package com.example.deliveryservice.model;

import com.example.deliveryservice.dto.CartItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter

public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String phoneNumber;
    private String address;

    @Enumerated(EnumType.STRING)
    private City city;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ElementCollection
    private List<CartItemDto> items;

    private BigDecimal totalAmount;

    private Long userId;
}

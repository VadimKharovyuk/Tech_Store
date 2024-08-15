package com.example.deliveryservice.dto;

import com.example.deliveryservice.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


    public DeliveryDTO(Long id, Long userId, String address, DeliveryStatus status) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.status = status.name(); // Преобразуем статус в строку
    }
}

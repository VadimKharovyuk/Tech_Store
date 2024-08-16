package com.example.deliveryservice.dto;
import com.example.deliveryservice.model.City;
import com.example.deliveryservice.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private City city;
    private DeliveryStatus status;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalAmount;
    private Long userId;


}

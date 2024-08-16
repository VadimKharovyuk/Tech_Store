package com.example.webservice.service;

import com.example.webservice.dto.CartItemDto;
import com.example.webservice.repository.DeliveryClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryService {
    private final DeliveryClient deliveryClient;
    public BigDecimal computeTotalAmount(List<CartItemDto> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemDto item : cartItems) {
            // Умножаем цену на количество и добавляем к общей сумме
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

}

package com.example.deliveryservice.service;

import com.example.deliveryservice.dto.CartItemDto;
import com.example.deliveryservice.dto.DeliveryDTO;
import com.example.deliveryservice.model.City;
import com.example.deliveryservice.model.Delivery;
import com.example.deliveryservice.model.DeliveryStatus;
import com.example.deliveryservice.repository.DeliveryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Delivery createDelivery(DeliveryDTO request) {
        // Логируем входные данные
        System.out.println("Received delivery request: " + request);

        Delivery delivery = new Delivery();
        delivery.setFullName(request.getFullName());
        delivery.setPhoneNumber(request.getPhoneNumber());
        delivery.setAddress(request.getAddress());

        // Преобразование City
        City city;
        try {
            city = City.fromCityName(request.getCity());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid city name: " + request.getCity(), e);
        }
        delivery.setCity(city);

        delivery.setStatus(DeliveryStatus.PAID);
        delivery.setItems(request.getItems());
        delivery.setTotalAmount(calculateTotalAmount(request.getItems()));
        delivery.setUserId(request.getUserId());

        // Сохраняем и возвращаем
        return deliveryRepository.save(delivery);
    }



    private BigDecimal calculateTotalAmount(List<CartItemDto> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }
}

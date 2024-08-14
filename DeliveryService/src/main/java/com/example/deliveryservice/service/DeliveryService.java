package com.example.deliveryservice.service;

import com.example.deliveryservice.dto.CartItemDto;
import com.example.deliveryservice.dto.DeliveryRequest;
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


    public Delivery createDelivery(DeliveryRequest request) {
        Delivery delivery = new Delivery();
        delivery.setFullName(request.getFullName());
        delivery.setPhoneNumber(request.getPhoneNumber());
        delivery.setAddress(request.getAddress());

        // Используйте fromCityName для преобразования строки в City
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

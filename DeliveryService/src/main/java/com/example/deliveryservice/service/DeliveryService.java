package com.example.deliveryservice.service;

import com.example.deliveryservice.dto.CartItemDto;
import com.example.deliveryservice.dto.DeliveryDTO;
import com.example.deliveryservice.model.City;
import com.example.deliveryservice.model.Delivery;
import com.example.deliveryservice.model.DeliveryStatus;
import com.example.deliveryservice.repository.DeliveryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//package com.example.deliveryservice.service;
//
//import com.example.deliveryservice.dto.CartItemDto;
//import com.example.deliveryservice.dto.DeliveryDTO;
//import com.example.deliveryservice.model.City;
//import com.example.deliveryservice.model.Delivery;
//import com.example.deliveryservice.model.DeliveryStatus;
//import com.example.deliveryservice.repository.DeliveryRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class DeliveryService {
//
//    private final DeliveryRepository deliveryRepository;
//
//    public Delivery createDelivery(DeliveryDTO request) {
//        // Логируем входные данные
//        System.out.println("Received delivery request: " + request);
//
//        Delivery delivery = new Delivery();
//        delivery.setFullName(request.getFullName());
//        delivery.setPhoneNumber(request.getPhoneNumber());
//        delivery.setAddress(request.getAddress());
//
//        // Преобразование City
//        City city;
//        try {
//            city = City.fromCityName(request.getCity());
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Invalid city name: " + request.getCity(), e);
//        }
//        delivery.setCity(city);
//
//        delivery.setStatus(DeliveryStatus.PAID);
//        delivery.setItems(request.getItems());
//        delivery.setTotalAmount(calculateTotalAmount(request.getItems()));
//        delivery.setUserId(request.getUserId());
//
//        // Сохраняем и возвращаем
//        return deliveryRepository.save(delivery);
//    }
//
//
//
//    private BigDecimal calculateTotalAmount(List<CartItemDto> items) {
//        return items.stream()
//                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }
//
//    public List<Delivery> getAllDeliveries() {
//        return deliveryRepository.findAll();
//    }
//
//    public List<DeliveryDTO> getDeliveriesByUserId(Long userId) {
//        return deliveryRepository.findById(userId).stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//    }
//    private DeliveryDTO convertToDto(Delivery delivery) {
//        // Конвертация модели Delivery в DeliveryDTO
//        return new DeliveryDTO(delivery.getId(), delivery.getUserId(), delivery.getAddress(), delivery.getStatus());
//    }
//}
@Service
@AllArgsConstructor
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Delivery createDelivery(DeliveryDTO request) {
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

        // Проверяем и инициализируем items
        List<CartItemDto> items = request.getItems() != null ? request.getItems() : new ArrayList<>();
        delivery.setItems(items);
        delivery.setTotalAmount(calculateTotalAmount(items));
        delivery.setUserId(request.getUserId());

        // Сохраняем и возвращаем
        return deliveryRepository.save(delivery);
    }


    private BigDecimal calculateTotalAmount(List<CartItemDto> items) {
        if (items == null) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public List<DeliveryDTO> getDeliveriesByUserId(Long userId) {
        return deliveryRepository.findByUserId(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DeliveryDTO convertToDto(Delivery delivery) {
        return new DeliveryDTO(
                delivery.getId(),
                delivery.getFullName(),
                delivery.getPhoneNumber(),
                delivery.getAddress(),
                delivery.getCity().name(), // или другой метод преобразования
                delivery.getStatus().name(), // или другой метод преобразования
                delivery.getItems(), // если CartItemDto и DeliveryDTO совместимы
                delivery.getTotalAmount(),
                delivery.getUserId()
        );
    }
}

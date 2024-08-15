package com.example.deliveryservice.controller;//package com.example.deliveryservice.controller;

import com.example.deliveryservice.dto.DeliveryDTO;
import com.example.deliveryservice.model.Delivery;
import com.example.deliveryservice.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;


//    @PostMapping
//    public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryDTO request) {
//
//        Delivery delivery = deliveryService.createDelivery(request);
//        return ResponseEntity.ok(delivery);
//    }
@PostMapping
public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryDTO request) {
    try {
        Delivery delivery = deliveryService.createDelivery(request);
        return ResponseEntity.ok(delivery);
    } catch (Exception e) {
        // Логируем ошибку и возвращаем сообщение об ошибке
        System.err.println("Error creating delivery: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
    }
}

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeliveryDTO>> getDeliveriesByUserId(@PathVariable Long userId) {
        List<DeliveryDTO> deliveries = deliveryService.getDeliveriesByUserId(userId);
        return ResponseEntity.ok(deliveries);
    }

}


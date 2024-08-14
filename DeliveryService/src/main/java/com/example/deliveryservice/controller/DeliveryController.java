package com.example.deliveryservice.controller;//package com.example.deliveryservice.controller;

import com.example.deliveryservice.dto.DeliveryDTO;
import com.example.deliveryservice.model.City;
import com.example.deliveryservice.model.Delivery;
import com.example.deliveryservice.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;


    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody DeliveryDTO request) {

        Delivery delivery = deliveryService.createDelivery(request);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }

}


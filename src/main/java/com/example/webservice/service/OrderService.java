package com.example.webservice.service;

import com.example.webservice.dto.DeliveryDTO;
import com.example.webservice.dto.DeliveryRequest;
import com.example.webservice.repository.DeliveryClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final DeliveryClient deliveryClient;

    public ResponseEntity createDelivery(DeliveryRequest deliveryRequest) {
        return deliveryClient.createDelivery(deliveryRequest);
    }

    public List<DeliveryDTO> getAllDeliveries() {
        return deliveryClient.listDeliveries();
    }
}

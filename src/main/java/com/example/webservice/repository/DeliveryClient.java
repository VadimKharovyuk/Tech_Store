package com.example.webservice.repository;


import com.example.webservice.dto.DeliveryDTO;
import com.example.webservice.dto.DeliveryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deliveryservice")
public interface DeliveryClient {

    @PostMapping("/deliveries")
    ResponseEntity createDelivery(@RequestBody DeliveryRequest deliveryRequest);

    @GetMapping("/deliveries")
    List<DeliveryDTO> listDeliveries();
}

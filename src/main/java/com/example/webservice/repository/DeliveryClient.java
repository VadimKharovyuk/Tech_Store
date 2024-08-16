package com.example.webservice.repository;

import com.example.webservice.dto.DeliveryDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deliveryservice")
public interface DeliveryClient {
    @PostMapping("/deliveries")
    DeliveryDTO createDelivery(@RequestBody DeliveryDTO deliveryRequest);

    @GetMapping("/deliveries")
    List<DeliveryDTO> listDeliveries();

    @GetMapping("/deliveries/user/{userId}")
    List<DeliveryDTO> listDeliveriesByUserId(@PathVariable("userId") Long userId);

}


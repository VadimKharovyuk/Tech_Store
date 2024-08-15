package com.example.deliveryservice.repository;

import com.example.deliveryservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;


public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
    List<Delivery> findByUserId(Long userId);

}

package com.example.webservice.repository;

import com.example.webservice.dto.CartDto;
import com.example.webservice.dto.CartItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cart-service", url = "http://192.168.1.103:5053")
public interface CartFeignClient {

    @PostMapping("/carts/items")
    ResponseEntity<Void> addItemToCart(@RequestBody CartItemDto cartItemDto);

    @GetMapping("/carts/{userId}")
    ResponseEntity<CartDto> getCart(@PathVariable("userId") Long userId);


}
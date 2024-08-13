package com.example.webservice.repository;
import com.example.webservice.dto.CartDto;
import com.example.webservice.dto.CartItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "cart-service")
public interface CartFeignClient {

    @PostMapping("/carts/items")
    ResponseEntity<Void> addItemToCart(@RequestBody CartItemDto cartItemDto);

    @GetMapping("/carts/{userId}")
    ResponseEntity<CartDto> getCart(@PathVariable("userId") Long userId);

    @PostMapping("/carts/items/{itemId}")
    ResponseEntity<Void> removeItemFromCart(@PathVariable("itemId") Long itemId, @RequestParam("userId") Long userId);

}

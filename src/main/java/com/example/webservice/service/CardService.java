package com.example.webservice.service;

import com.example.webservice.dto.CartItemDto;
import com.example.webservice.repository.CartFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {


    @Autowired
    private CartFeignClient cartFeignClient;


    public void addProductToCart(CartItemDto cartItemDto) {
        cartFeignClient.addItemToCart(cartItemDto);
    }
}

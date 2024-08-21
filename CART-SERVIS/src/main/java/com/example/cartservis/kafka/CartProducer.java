package com.example.cartservis.kafka;

import com.example.cartservis.dto.CartItemDto;
import com.example.cartservis.model.CartItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CartProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void sendAddToCartEvent(CartItemDto cartItemDto) {
        try {
            // Сериализация объекта CartItemDto в строку JSON
            String message = objectMapper.writeValueAsString(cartItemDto);

            // Отправка сообщения в Kafka
            kafkaTemplate.send("cart-topic", message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


//
//package com.example.cartservis.kafka;
//
//import com.example.cartservis.dto.CartItemDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class CartProducer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private final ObjectMapper objectMapper;
//
//    public void sendAddToCartEvent(CartItemDto cartItemDto) {
//        try {
//            // Создание строки сообщения с форматированием
//            String message = String.format("Product added to cart: ID=%d, Product ID=%d, Name=%s, Price=%s, Quantity=%d, User ID=%d",
//                    cartItemDto.getId(),
//                    cartItemDto.getProductId(),
//                    cartItemDto.getProductName(),
//                    cartItemDto.getPrice(),
//                    cartItemDto.getQuantity(),
//                    cartItemDto.getUserId()
//            );
//            // Отправка сообщения в Kafka
//            kafkaTemplate.send("cart-topic", message);
//        } catch (Exception e) {
//            // Обработка исключений при сериализации
//            e.printStackTrace();
//        }
//    }
//}

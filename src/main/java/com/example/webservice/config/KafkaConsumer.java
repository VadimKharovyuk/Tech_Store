package com.example.webservice.config;
import com.example.webservice.dto.CartItemDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
     @KafkaListener(topics = "product-topic", groupId = "web-service-group")
     public void listen(String  masagge) {

          System.out.println("Received product: " + masagge);
     }
     @KafkaListener(topics = "cart-topic", groupId = "web-service-group")
     public void listenCard(String cartItemDto) {
          System.out.println("Received cart item: " + cartItemDto);
     }


}

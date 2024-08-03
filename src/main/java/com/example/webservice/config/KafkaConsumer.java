package com.example.webservice.config;

import com.example.webservice.dto.Product;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
     @KafkaListener(topics = "product-topic", groupId = "web-service-group")
     public void listen(String  masagge) {
          // Обработка полученного продукта
          System.out.println("Received product: " + masagge);
     }
}

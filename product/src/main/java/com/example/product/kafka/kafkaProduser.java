package com.example.product.kafka;

import com.example.product.model.Product;
import lombok.AllArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class kafkaProduser {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendProductUpdate(Product product) {
        String message = String.format("Product added: ID=%d, Name=%s, Price=%s",
                product.getId(),
                product.getName(),
                product.getPrice());
        kafkaTemplate.send("product-topic", message);
    }



}

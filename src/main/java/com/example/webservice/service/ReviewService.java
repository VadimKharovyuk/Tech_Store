package com.example.webservice.service;

import com.example.webservice.dto.ReviewDTO;
import com.example.webservice.exeption.ReviewNotFoundException;
import com.example.webservice.repository.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ProductFeignClient productFeignClient;

    public ReviewDTO fetchReview(Long id) {
        // Получаем Optional<ReviewDTO> от Feign Client
        Optional<ReviewDTO> reviewOptional = productFeignClient.getReview(id);

        // Если Optional не содержит значение, выбрасываем исключение
        return reviewOptional.orElseThrow(() -> new ReviewNotFoundException("Review not found for ID: " + id));
    }

    public void createReview(ReviewDTO reviewDTO) {
        productFeignClient.createReview(reviewDTO);
    }


}

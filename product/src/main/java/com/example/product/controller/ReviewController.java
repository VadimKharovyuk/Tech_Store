package com.example.product.controller;

import com.example.product.Mapper.ReviewMapper;
import com.example.product.dto.ReviewDTO;
import com.example.product.model.Review;
import com.example.product.repository.ProductRepository;
import com.example.product.repository.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {

    private  final ReviewService reviewService;
    private  final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        // Преобразуем DTO в сущность Review
        Review review = ReviewMapper.toEntity(reviewDTO, productRepository);
        // Сохраняем отзыв
        Review savedReview = reviewService.saveReview(review);
        // Преобразуем сохраненный отзыв обратно в DTO
        ReviewDTO savedReviewDTO = ReviewMapper.toDTO(savedReview);
        return ResponseEntity.ok(savedReviewDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        // Получаем отзыв по ID
        Optional<Review> reviewOptional = reviewService.getReviewById(id);
        if (reviewOptional.isPresent()) {
            // Преобразуем отзыв в DTO и возвращаем
            ReviewDTO reviewDTO = ReviewMapper.toDTO(reviewOptional.get());
            return ResponseEntity.ok(reviewDTO);
        } else {
            // Отзыв не найден
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        // Проверяем наличие отзыва перед удалением
        Optional<Review> reviewOptional = reviewService.getReviewById(id);
        if (reviewOptional.isPresent()) {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build(); // Успешное удаление без контента в ответе
        } else {
            // Отзыв не найден
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
    @PostMapping("/product/delete/{productId}")
    public ResponseEntity<Void> deleteReviewsByProductId(@PathVariable Long productId) {
        // Проверяем наличие отзывов для данного продукта
        List<ReviewDTO> reviews = reviewService.getReviewsByProductId(productId);
        if (!reviews.isEmpty()) {
            // Удаляем все отзывы для данного продукта
            reviewService.deleteReviewsByProductId(productId);
            return ResponseEntity.noContent().build(); // Успешное удаление без контента в ответе
        } else {
            // Отзывы не найдены
            return ResponseEntity.notFound().build();
        }
    }




}

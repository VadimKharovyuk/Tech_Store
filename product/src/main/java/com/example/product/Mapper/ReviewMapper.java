package com.example.product.Mapper;


import com.example.product.dto.ReviewDTO;
import com.example.product.model.Review;
import com.example.product.repository.ProductRepository;

public class ReviewMapper {

    private static ProductRepository productRepository; // Сервис или репозиторий для получения продукта

    public static ReviewDTO toDTO(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setReviewerName(review.getReviewerName());
        dto.setContent(review.getContent());
        dto.setRating(review.getRating());
        dto.setProductId(review.getProduct() != null ? review.getProduct().getId() : null);
        return dto;
    }

    public static Review toEntity(ReviewDTO dto, ProductRepository productRepository) {
        if (dto == null) {
            return null;
        }

        Review review = new Review();
        review.setId(dto.getId());
        review.setReviewerName(dto.getReviewerName());
        review.setContent(dto.getContent());
        review.setRating(dto.getRating());

        if (dto.getProductId() != null) {
            review.setProduct(productRepository.findById(dto.getProductId()).orElse(null));
        }

        return review;
    }
}

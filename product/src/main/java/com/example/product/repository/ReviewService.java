package com.example.product.repository;

import com.example.product.dto.ReviewDTO;
import com.example.product.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review saveReview(Review review);


    Optional<Review> getReviewById(Long id);

    void deleteReview(Long id);

    List<ReviewDTO> getReviewsByProductId(Long productId);


    void deleteReviewsByProductId(Long productId);

}

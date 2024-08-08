package com.example.webservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long id;
    private String reviewerName;
    private String content;
    private int rating; // Оценка от 1 до 5
    private Long productId; // Ссылка на ID продукта, к которому относится отзыв





}

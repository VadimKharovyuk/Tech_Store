package com.example.webservice.controller;

import com.example.webservice.dto.Product;
import com.example.webservice.dto.ReviewDTO;
import com.example.webservice.exeption.ReviewNotFoundException;
import com.example.webservice.repository.ProductFeignClient;
import com.example.webservice.service.ProductService;
import com.example.webservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {


    private final ReviewService reviewService;
    private final ProductService productService;


    @GetMapping("/create/{productId}")
    public String showCreateReviewForm(@PathVariable Long productId, Model model) {
        // Создаем новый объект ReviewDTO и устанавливаем ID продукта
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setProductId(productId);

        model.addAttribute("review", reviewDTO);
        return "Review/createReview";
    }

    @PostMapping("/create")
    public String createReview(@ModelAttribute ReviewDTO reviewDTO) {
        reviewService.createReview(reviewDTO);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String getReview(@PathVariable Long id, Model model) {
        try {
            ReviewDTO reviewDTO = reviewService.fetchReview(id);
            model.addAttribute("review", reviewDTO);
            return "Review/viewReview";
        } catch (ReviewNotFoundException e) {
            return "redirect:/reviews/list?error=ReviewNotFound";
        }
    }


    @GetMapping("/list/{productId}")
    public String listReviews(@PathVariable Long productId, Model model) {
        // Получаем список отзывов по ID продукта
        List<ReviewDTO> reviews = productService.getReviewsByProductId(productId);

        // Получаем название товара по ID продукта
        Product product = productService.getProductById(productId);
        String productName = product.getName();

        // Добавляем данные в модель
        model.addAttribute("reviews", reviews);
        model.addAttribute("productId", productId);
        model.addAttribute("productName", productName);

        return "Review/listReviews";
    }










        @PostMapping("/delete/{id}")
        public String deleteReview (@PathVariable Long id){
            reviewService.deleteReview(id);
            return "redirect:/reviews/list";
        }
    }


package com.example.webservice.service;

import com.example.webservice.dto.CartItemDto;
import com.example.webservice.dto.Product;
import com.example.webservice.dto.ReviewDTO;
import com.example.webservice.repository.CartFeignClient;
import com.example.webservice.repository.ProductFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductFeignClient productFeignClient;
    private  final CartFeignClient cartFeignClient;

    public Product getProduct(Long id) {
        return productFeignClient.getproductById(id);
    }

    public Product updateProduct(Long id, Product product) {
        return productFeignClient.updateProduct(id, product);
    }

    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        return productFeignClient.getReviewsByProductId(productId);
    }

    public Product getProductById(Long productId) {
       return productFeignClient.getproductById(productId);
    }
    public void deleteRewiresById(@PathVariable Long id){
     productFeignClient.deleteReview(id);
    }



}

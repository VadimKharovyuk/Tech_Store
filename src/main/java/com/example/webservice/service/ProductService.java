package com.example.webservice.service;

import com.example.webservice.dto.CartItemDto;
import com.example.webservice.dto.Product;
import com.example.webservice.dto.ReviewDTO;
import com.example.webservice.repository.CartFeignClient;
import com.example.webservice.repository.ProductFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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


    public void addProductToCart(Long userId, Long productId, String productName, int quantity) {
        // Создание DTO для корзины
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setUserId(userId);
        cartItemDto.setProductId(productId);
        cartItemDto.setProductName(productName);
        cartItemDto.setQuantity(quantity);

        try {
            // Вызов метода CartFeignClient для добавления товара в корзину
            ResponseEntity<Void> response = cartFeignClient.addItemToCart(cartItemDto);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                // Товар успешно добавлен
                System.out.println("Product added to cart successfully.");
            } else {
                // Обработка случаев, когда корзина не найдена или произошла ошибка
                System.err.println("Failed to add product to cart: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // Логирование ошибки и обработка исключений
            System.err.println("Exception occurred while adding product to cart: " + e.getMessage());
        }
    }

    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        return productFeignClient.getReviewsByProductId(productId);
    }

    public Product getProductById(Long productId) {
       return productFeignClient.getproductById(productId);
    }
}

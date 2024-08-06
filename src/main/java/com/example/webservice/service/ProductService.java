package com.example.webservice.service;

import com.example.webservice.dto.Product;
import com.example.webservice.repository.ProductFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductFeignClient productFeignClient;

    public Product getProduct(Long id) {
        return productFeignClient.getproductById(id);
    }

    public Product updateProduct(Long id, Product product) {
        return productFeignClient.updateProduct(id, product);
    }
}

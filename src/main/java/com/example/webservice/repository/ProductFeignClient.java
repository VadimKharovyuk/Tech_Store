package com.example.webservice.repository;

import com.example.webservice.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/api/products")
    List<Product> getAllProducts();


    @PostMapping("/api/products/add")
    Product save( @RequestBody  Product product);
}

package com.example.webservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;


@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes()));
        };
    }
}

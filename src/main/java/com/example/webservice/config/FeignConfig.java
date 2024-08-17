package com.example.webservice.config;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 30000); // Тайм-аут подключения и тайм-аут чтения (в миллисекундах)
    }

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes()));
        };
    }


}

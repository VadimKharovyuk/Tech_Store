package com.example.webservice.config;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}

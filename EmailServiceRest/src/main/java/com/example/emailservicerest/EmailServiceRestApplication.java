package com.example.emailservicerest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailServiceRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailServiceRestApplication.class, args);
    }

}

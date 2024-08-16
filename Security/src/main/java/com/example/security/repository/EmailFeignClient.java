package com.example.security.repository;

import com.example.security.dto.EmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "EMAILSERVICEREST")
public interface EmailFeignClient {

    @PostMapping("/api/email/send")
    String sendEmail(@RequestBody EmailRequest emailRequest);

    @PostMapping("/api/email/password")
    ResponseEntity<Void> sendEmailPassword(@RequestParam("email") String email, @RequestParam("pass") String pass);
}





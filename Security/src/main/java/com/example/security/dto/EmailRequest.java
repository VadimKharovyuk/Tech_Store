package com.example.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class EmailRequest {
    private String to;
    private String subject;
    private String body;


}

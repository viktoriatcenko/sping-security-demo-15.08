package ru.maxima.spring_security_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Status {
    private Long statusCode;
    private String statusDesc;
}

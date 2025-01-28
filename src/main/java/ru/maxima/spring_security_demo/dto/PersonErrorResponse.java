package ru.maxima.spring_security_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class PersonErrorResponse {
    private Date date;
    private Status status;
}

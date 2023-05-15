package com.sank.bookshop.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
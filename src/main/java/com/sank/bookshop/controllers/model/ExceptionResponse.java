package com.sank.bookshop.controllers.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@JsonPropertyOrder({"message", "timestamp", "details"})
public class ExceptionResponse {
    @ApiModelProperty(position = 1, example = "2020-05-16T10:57:47.1080863")
    private LocalDateTime timestamp;
    @ApiModelProperty(example = "Error While processing data")
    private String message;
    @ApiModelProperty(position = 2, example = "uri=/some/api/url")
    private String details;
}
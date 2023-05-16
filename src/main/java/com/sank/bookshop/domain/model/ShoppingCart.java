package com.sank.bookshop.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShoppingCart {
    @ApiModelProperty(example = "123456789123456789")
    private String isbn;
    @ApiModelProperty(example = "2")
    private Integer quantity;
}

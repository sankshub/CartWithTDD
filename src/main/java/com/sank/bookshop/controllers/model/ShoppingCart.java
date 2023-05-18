package com.sank.bookshop.controllers.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCart {
    @ApiModelProperty(example = "123456789123456789")
    private String isbn;
    @ApiModelProperty(example = "2")
    private Integer quantity;
}

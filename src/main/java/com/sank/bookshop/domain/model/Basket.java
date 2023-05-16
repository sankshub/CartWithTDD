package com.sank.bookshop.domain.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Basket {
    private List<BookResponse> books;
    @ApiModelProperty(position = 1, example = "25%")
    private String discountApplied;
    @ApiModelProperty(position = 2, example = "225.00€")
    private String totalCostOfBasket;
    @ApiModelProperty(position = 3, example = "225.00€")
    private String totalCostOfBasketWithDiscount;

}

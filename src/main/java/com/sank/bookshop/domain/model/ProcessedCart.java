package com.sank.bookshop.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ProcessedCart {
    List<Basket> basket;
    @ApiModelProperty(position = 1, example = "800.00€")
    String totalCost;
    @ApiModelProperty(position = 2, example = "500.00€")
    String totalCostWithDiscount;
}

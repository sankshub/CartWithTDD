package com.sank.bookshop.controllers.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Invoice {
    List<GroupedBooks> groupedBooks;
    @ApiModelProperty(position = 1, example = "800.00€")
    String totalCost;
    @ApiModelProperty(position = 2, example = "500.00€")
    String totalCostWithDiscount;
}

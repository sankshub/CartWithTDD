package com.sank.bookshop.controllers.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupedBooks {
    private List<BookResponse> books;
    @ApiModelProperty(position = 1, example = "25%")
    private String discountApplied;
    @ApiModelProperty(position = 2, example = "225.00€")
    private String totalCostOfGroup;
    @ApiModelProperty(position = 3, example = "225.00€")
    private String totalCostOfGroupWithDiscount;

}

package com.sank.bookshop.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Offers {
    @ApiModelProperty(position = 1, example = "5")
    private Integer uniqueCopies;
    @ApiModelProperty(position = 2, example = "25%")
    private String discount;
}
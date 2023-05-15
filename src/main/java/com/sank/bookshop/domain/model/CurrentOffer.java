package com.sank.bookshop.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CurrentOffer {
    @ApiModelProperty(example = "More books more savings")
    private String offerMessage;
    List<Offers> offers;
}

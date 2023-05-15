package com.sank.bookshop.services.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class DiscountedCart {
    List<UniqueBasket> uniqueBasket;
    Double totalCost;
    Double totalCostWithDiscount;
}

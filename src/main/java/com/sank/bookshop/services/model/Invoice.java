package com.sank.bookshop.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Invoice {
    List<UniqueBooksSet> uniqueBooksSet;
    Double totalCost;
    Double totalCostWithDiscount;
}

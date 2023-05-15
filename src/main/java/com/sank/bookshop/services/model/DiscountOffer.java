package com.sank.bookshop.services.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DiscountOffer {
    private String offerMessage;
    private List<Offers> offers;
}

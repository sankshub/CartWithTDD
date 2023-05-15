package com.sank.bookshop.services.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShoppingCart {
    List<CartItem> items;
}

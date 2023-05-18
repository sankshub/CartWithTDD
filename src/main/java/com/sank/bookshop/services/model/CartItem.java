package com.sank.bookshop.services.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItem {
    private Book book;
    private Integer quantity;

    public void changeQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}

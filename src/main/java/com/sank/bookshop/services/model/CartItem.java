package com.sank.bookshop.services.model;


import com.sank.bookshop.repos.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {
    private Book book;
    private Integer quantity;

    public void changeQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

package com.sank.bookshop.services.service.model;


import com.sank.bookshop.repos.entity.Book;
import lombok.Data;

import java.util.Set;

@Data
public class UniqueBasket {
    private final Set<Book> books;
    private final Integer discountApplied;
    private Double costOfBasketWithDiscount;

    public UniqueBasket(Set<Book> books, Integer discountApplied) {
        this.books = books;
        this.discountApplied = discountApplied;
    }
}

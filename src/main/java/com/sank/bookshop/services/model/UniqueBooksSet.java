package com.sank.bookshop.services.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UniqueBooksSet {
    private final Set<Book> books;
    private final Integer discountApplied;
    private Double costOfBookSetWithDiscount;

    public UniqueBooksSet(Set<Book> books, Integer discountApplied) {
        this.books = books;
        this.discountApplied = discountApplied;
    }
}

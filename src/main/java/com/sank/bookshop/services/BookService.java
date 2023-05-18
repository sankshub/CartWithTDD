package com.sank.bookshop.services;

import com.sank.bookshop.services.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByIsbn(String isbn);

}

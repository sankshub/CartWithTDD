package com.sank.bookshop.services.service;

import com.sank.bookshop.repos.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findByIsbn(String isbn);

}

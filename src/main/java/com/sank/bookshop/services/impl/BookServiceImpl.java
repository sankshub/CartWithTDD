package com.sank.bookshop.services.impl;

import com.sank.bookshop.exception.BookNotFoundException;
import com.sank.bookshop.services.BookService;
import com.sank.bookshop.services.model.Book;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Override
    public List<Book> findAll() throws BookNotFoundException {
        return Arrays.asList(Book.values());
    }

    @Override
    public Book findByIsbn(String isbn) throws BookNotFoundException {
        return Book.findBookByISBN(isbn);
    }
}

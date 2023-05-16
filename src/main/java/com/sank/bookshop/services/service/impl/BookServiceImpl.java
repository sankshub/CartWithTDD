package com.sank.bookshop.services.service.impl;

import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.repos.repository.BookRepository;
import com.sank.bookshop.services.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findByIsbn(String isbn) {
        List<Book> bookList = bookRepository.findAll();
        return bookList.stream()
                       .filter(book -> StringUtils.equalsIgnoreCase(StringUtils.trim(book.getIsbn()), isbn))
                       .findAny()
                       .orElse(null);
    }
}

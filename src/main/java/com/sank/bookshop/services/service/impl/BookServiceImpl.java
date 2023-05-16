package com.sank.bookshop.services.service.impl;

import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.repos.repository.BookWareHouse;
import com.sank.bookshop.services.exception.BookNotFoundException;
import com.sank.bookshop.services.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final String BOOK_NOT_FOUND_ERROR = "Requested book not found/ISBN is null, Try again with valid ISBN ";
    @Autowired
    BookWareHouse bookWareHouse;

    @Override
    public List<Book> findAll() {
        return bookWareHouse.findAll();
    }

    @Override
    public Book findByIsbn(String isbn) {
        if (StringUtils.isBlank(isbn)) throw new BookNotFoundException(BOOK_NOT_FOUND_ERROR);
        List<Book> bookList = bookWareHouse.findAll();
        return bookList.stream()
                       .filter(book -> StringUtils.equalsIgnoreCase(StringUtils.trim(book.getIsbn()), isbn))
                       .findAny()
                       .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND_ERROR));
    }
}

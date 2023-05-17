package com.sank.bookshop.services.service.impl;

import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.repos.repository.BookWareHouse;
import com.sank.bookshop.services.exception.BookNotFoundException;
import com.sank.bookshop.services.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private static final String BOOK_NOT_FOUND_ERROR = "Requested book not found/ISBN is null, Try again with valid ISBN ";
    private static final String NO_BOOKS_ERROR = "No books available now, we will be back shortly ";
    @Autowired
    BookWareHouse bookWareHouse;

    @Override
    public List<Book> findAll() throws BookNotFoundException {
        List<Book> books = bookWareHouse.findAll();
        if (CollectionUtils.isEmpty(books)) throw new BookNotFoundException(NO_BOOKS_ERROR);
        return bookWareHouse.findAll();
    }

    @Override
    public Book findByIsbn(String isbn) throws BookNotFoundException {
        if (StringUtils.isBlank(isbn)) throw new BookNotFoundException(BOOK_NOT_FOUND_ERROR);
        List<Book> bookList = bookWareHouse.findAll();
        return bookList.stream()
                       .filter(book -> StringUtils.equalsIgnoreCase(StringUtils.trim(book.getIsbn()), isbn))
                       .findAny()
                       .orElseThrow(() -> new BookNotFoundException(BOOK_NOT_FOUND_ERROR));
    }
}

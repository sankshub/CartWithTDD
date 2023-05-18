package com.sank.bookshop.services;

import com.sank.bookshop.exception.BookNotFoundException;
import com.sank.bookshop.services.impl.BookServiceImpl;
import com.sank.bookshop.services.model.Book;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.sank.bookshop.services.model.Constants.BOOK_NOT_FOUND_ERROR;

@RunWith(MockitoJUnitRunner.class)
class BookServiceTest {
    BookServiceImpl bookService = new BookServiceImpl();


    @Test
    void findBookByIsbn() {
        Assert.assertEquals(bookService.findByIsbn("12345"), Book.CLEAN_CODE);
    }

    @Test
    void findBookWithUnknownIsbn() {
        Exception exception = Assert.assertThrows(BookNotFoundException.class, () -> {
            bookService.findByIsbn("123456789");
        });
        Assert.assertEquals(BOOK_NOT_FOUND_ERROR, exception.getMessage());
    }

    @Test
    void findBookWithNull() {
        Exception exception = Assert.assertThrows(BookNotFoundException.class, () -> {
            bookService.findByIsbn(null);
        });
        Assert.assertEquals(BOOK_NOT_FOUND_ERROR, exception.getMessage());
    }

    @Test
    void findAllBooks() {
        Assert.assertTrue(bookService.findAll().contains(Book.CLEAN_CODE));
    }

}
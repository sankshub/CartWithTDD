package com.sank.bookshop.services.service.impl;

import com.sank.bookshop.repos.entity.Author;
import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.repos.repository.BookRepository;
import com.sank.bookshop.services.exception.BookNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    private static final String BOOK_NOT_FOUND_ERROR = "Requested book not found/ISBN is null, Try again with valid ISBN ";
    @Mock
    BookRepository repository;
    @InjectMocks
    BookServiceImpl bookService;
    Book book = new Book();

    @Before
    public void setUp() {
        book.setIsbn("123456789");
        book.setTitle("Clean Code");
        Author author = new Author();
        author.setFirstName("Robert");
        author.setMiddleName(null);
        author.setLastName("Martin");
        book.setAuthor(author);
        book.setYearOfPublish("2008");
        book.setPrice("50");
    }

    @Test
    public void findByIsbn() {
        when(repository.findAll()).thenReturn(Collections.singletonList(book));
        Assert.assertEquals(bookService.findByIsbn("123456789"), book);
    }

    @Test
    public void findBookWithUnknownIsbn() {
        when(repository.findAll()).thenReturn(Collections.emptyList());
        Exception exception = Assert.assertThrows(BookNotFoundException.class, () -> {
            bookService.findByIsbn("123456789");
        });
        Assert.assertEquals(BOOK_NOT_FOUND_ERROR, exception.getMessage());
    }

    @Test
    public void findBookWithNull() {
        Exception exception = Assert.assertThrows(BookNotFoundException.class, () -> {
            bookService.findByIsbn(null);
        });
        Assert.assertEquals(BOOK_NOT_FOUND_ERROR, exception.getMessage());
    }
}
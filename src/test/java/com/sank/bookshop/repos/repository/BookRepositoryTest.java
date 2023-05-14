package com.sank.bookshop.repos.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BookRepositoryTest {
    @Test
    public void testAlwaysReturnsNonNull() {
        BookRepository bookRepository = new BookRepository();
        Assert.assertNotNull(bookRepository.findAll());
    }
}

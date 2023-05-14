package com.sank.bookshop.repos.repository;

import com.sank.bookshop.repos.entity.Author;
import com.sank.bookshop.repos.entity.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookRepositoryTest {
    @Mock
    Resource resource;
    Book book = new Book();
    @Mock
    private ResourceLoader resourceLoader;
    @InjectMocks
    private BookRepository bookRepository;

    @Before
    public void setup() {
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
    public void testAlwaysReturnsNonNull() throws IOException {
        when(resourceLoader.getResource(Mockito.anyString())).thenReturn(resource);
        when(resource.getFile()).thenReturn(new File("test"));
        Assert.assertNotNull(bookRepository.findAll());
    }

    @Test
    public void testReadAJsonFile() throws IOException, URISyntaxException {
        when(resourceLoader.getResource(Mockito.anyString())).thenReturn(resource);
        when(resource.getFile()).thenReturn(new File(Objects.requireNonNull(this.getClass()
                                                                                .getResource("/masterbookfeed.json"))
                                                            .toURI()));
        Assert.assertFalse(CollectionUtils.isEmpty(bookRepository.findAll()));
    }

    @Test
    public void testReturnEmptyListDuringException() throws IOException {
        when(resourceLoader.getResource(Mockito.anyString())).thenReturn(resource);
        when(resource.getFile()).thenThrow(FileNotFoundException.class);
        Assert.assertNotNull(bookRepository.findAll());
    }

    @Test
    public void testReturnsBookList() throws IOException, URISyntaxException {
        when(resourceLoader.getResource(Mockito.anyString())).thenReturn(resource);
        when(resource.getFile()).thenReturn(new File(Objects.requireNonNull(this.getClass()
                                                                                .getResource("/masterbookfeed.json"))
                                                            .toURI()));
        Assert.assertTrue(bookRepository.findAll().contains(book));
    }
}

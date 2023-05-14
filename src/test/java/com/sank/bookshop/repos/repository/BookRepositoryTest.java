package com.sank.bookshop.repos.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookRepositoryTest {
    @Mock
    Resource resource;
    @Mock
    private ResourceLoader resourceLoader;
    @InjectMocks
    private BookRepository bookRepository;

    @Test
    public void testAlwaysReturnsNonNull() throws IOException {
        when(resourceLoader.getResource(Mockito.anyString())).thenReturn(resource);
        when(resource.getFile()).thenReturn(new File("test"));
        Assert.assertNotNull(bookRepository.findAll());
    }

    @Test
    public void testReadAJsonFile() throws IOException, URISyntaxException {
        when(resourceLoader.getResource(Mockito.anyString())).thenReturn(resource);
        when(resource.getFile()).thenReturn(new File(this.getClass()
                                                         .getResource("/masterbookfeed.json")
                                                         .toURI()));
        Assert.assertTrue(bookRepository.findAll().isEmpty());
    }

    @Test
    public void testReturnEmptyListDuringException() throws IOException {
        when(resourceLoader.getResource(Mockito.anyString())).thenReturn(resource);
        when(resource.getFile()).thenThrow(FileNotFoundException.class);
        Assert.assertNotNull(bookRepository.findAll());
    }
}

package com.sank.bookshop.domain;

import com.sank.bookshop.domain.mappers.RequestResponseMapperImpl;
import com.sank.bookshop.repos.entity.Author;
import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.services.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingControllerTest {

    private String bookJson = "[{\"title\":\"Clean Code\",\"isbn\":\"123456789\",\"authorFullName\":null,\"yearOfPublish\":\"2008\",\"price\":\"50\"}]";
    Book cleanCodeBook = new Book();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookServiceImpl bookService;
    @MockBean
    private RequestResponseMapperImpl requestResponseMapper;

    @BeforeEach
    public void setup() {
        cleanCodeBook.setIsbn("123456789");
        cleanCodeBook.setTitle("Clean Code");
        Author cleanCodeBookAuthor = new Author();
        cleanCodeBookAuthor.setFirstName("Robert");
        cleanCodeBookAuthor.setMiddleName(null);
        cleanCodeBookAuthor.setLastName("Martin");
        cleanCodeBook.setAuthor(cleanCodeBookAuthor);
        cleanCodeBook.setYearOfPublish("2008");
        cleanCodeBook.setPrice("50");
    }

    @Test
    void shouldGive200Response() throws Exception {
        when(bookService.findAll()).thenReturn(Collections.emptyList());
        mvc.perform(get("/api/books/"))
           .andExpect(status().isOk());
    }

    @Test
    void shouldGetBookResponse() throws Exception {
        when(bookService.findAll()).thenReturn(Collections.singletonList(cleanCodeBook));
        when(requestResponseMapper.mapToBookModelList(Mockito.anyList())).thenCallRealMethod();
        mvc.perform(get("/api/books/"))
           .andExpect(status().isOk())
           .andExpect(content().string(bookJson));
    }
}

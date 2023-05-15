package com.sank.bookshop.domain;

import com.sank.bookshop.services.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookServiceImpl bookService;


    @Test
    void shouldGive200Response() throws Exception {
        when(bookService.findAll()).thenReturn(Collections.emptyList());
        mvc.perform(get("/api/books/"))
           .andExpect(status().isOk());
    }
}

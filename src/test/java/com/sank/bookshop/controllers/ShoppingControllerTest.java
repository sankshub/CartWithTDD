package com.sank.bookshop.controllers;

import com.sank.bookshop.mappers.RequestResponseMapperImpl;
import com.sank.bookshop.services.impl.BookServiceImpl;
import com.sank.bookshop.services.impl.UniqueSetOfBooksDiscount;
import com.sank.bookshop.services.model.ShoppingCart;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private BookServiceImpl bookService;
    @MockBean
    private UniqueSetOfBooksDiscount discountService;
    @MockBean
    private RequestResponseMapperImpl requestResponseMapper;

    @Test
    void shouldGet200ResponseForBookApi() throws Exception {
        when(bookService.findAll()).thenReturn(Collections.emptyList());
        mvc.perform(get("/api/books/"))
           .andExpect(status().isOk());
    }

    @Test
    void shouldGetBookAsJsonResponse() throws Exception {

        when(bookService.findAll()).thenCallRealMethod();
        when(requestResponseMapper.mapToBookModelList(Mockito.anyList())).thenCallRealMethod();

        String cleanCodeBookJson = "[{\"title\":\"Clean Code\",\"isbn\":\"12345\",\"yearOfPublish\":\"2008\",\"price\":\"50\",\"authorName\":\"Robert Martin\"},{\"title\":\"The Clean Coder\",\"isbn\":\"23451\",\"yearOfPublish\":\"2011\",\"price\":\"50\",\"authorName\":\"Robert Martin\"},{\"title\":\"Clean Architecture\",\"isbn\":\"34512\",\"yearOfPublish\":\"2017\",\"price\":\"50\",\"authorName\":\"Robert Martin\"},{\"title\":\"Test Driven Development by Example\",\"isbn\":\"45123\",\"yearOfPublish\":\"2003\",\"price\":\"50\",\"authorName\":\"Kent Beck\"},{\"title\":\"Working Effectively With Legacy Code\",\"isbn\":\"51234\",\"yearOfPublish\":\"2004\",\"price\":\"50\",\"authorName\":\"Michael C. Feathers\"}]> but was:<[{\"title\":\"Clean Code\",\"isbn\":\"12345\",\"yearOfPublish\":\"2008\",\"price\":\"50\",\"authorName\":\"Robert Martin\"},{\"title\":\"The Clean Coder\",\"isbn\":\"23451\",\"yearOfPublish\":\"2011\",\"price\":\"50\",\"authorName\":\"Robert Martin\"},{\"title\":\"Clean Architecture\",\"isbn\":\"34512\",\"yearOfPublish\":\"2017\",\"price\":\"50\",\"authorName\":\"Robert Martin\"},{\"title\":\"Test Driven Development by Example\",\"isbn\":\"45123\",\"yearOfPublish\":\"2003\",\"price\":\"50\",\"authorName\":\"Kent Beck\"},{\"title\":\"Working Effectively With Legacy Code\",\"isbn\":\"51234\",\"yearOfPublish\":\"2004\",\"price\":\"50\",\"authorName\":\"Michael C. Feathers\"}]";
        mvc.perform(get("/api/books/"))
           .andExpect(status().isOk())
           .andExpect(content().json(cleanCodeBookJson));
    }

    @Test
    void shouldGet200ResponseForInvoiceApi() throws Exception {
        when(discountService.applyDiscount(Mockito.mock(ShoppingCart.class))).thenCallRealMethod();
        mvc.perform(post("/api/processDiscount").content("[{  \"isbn\": 12345,  \"quantity\": 2}]")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(status().isOk());
    }

    @Test
    void shouldGet404ResponseForInvoiceApi() throws Exception {
        when(discountService.applyDiscount(Mockito.mock(ShoppingCart.class))).thenCallRealMethod();
        when(bookService.findByIsbn(Mockito.anyString())).thenCallRealMethod();
        mvc.perform(post("/api/processDiscount").content("[\n" +
                   "{" +
                   "\"isbn\": 123456666," +
                   "\"quantity\": 2" +
                   "}]").contentType(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(status().isNotFound());
    }

    @Test
    void shouldGet400ResponseForInvoiceApi() throws Exception {
        when(discountService.applyDiscount(Mockito.any(ShoppingCart.class))).thenCallRealMethod();
        when(bookService.findByIsbn(Mockito.anyString())).thenCallRealMethod();
        when(requestResponseMapper.mapToShoppingCartEntity(Mockito.anyList(), Mockito.any(BookServiceImpl.class))).thenCallRealMethod();
        mvc.perform(post("/api/processDiscount").content("[\n" +
                   "{" +
                   "\"isbn\": 12345," +
                   "\"quantity\": 0" +
                   "}]").contentType(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetDiscountedInvoiceResponseForInvoiceApi() throws Exception {
        when(discountService.applyDiscount(Mockito.any(ShoppingCart.class))).thenCallRealMethod();
        when(bookService.findByIsbn(Mockito.anyString())).thenCallRealMethod();
        when(requestResponseMapper.mapToShoppingCartEntity(Mockito.anyList(), Mockito.any(BookServiceImpl.class))).thenCallRealMethod();
        mvc.perform(post("/api/processDiscount").content("[{\"isbn\": 12345,\"quantity\": 2},{\"isbn\": 23451,\"quantity\": 2},{\"isbn\": 34512,\"quantity\": 2},{\"isbn\": 45123,\"quantity\": 1},{\"isbn\": 51234,\"quantity\": 1}]")
                                                .contentType(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(status().isOk()).andExpect(content().string(Matchers.containsString("groupedBooks")));
    }

    @Test
    void shouldGet500ResponseForInvoiceApi() throws Exception {
        when(bookService.findByIsbn(Mockito.anyString())).thenThrow(NullPointerException.class);
        mvc.perform(post("/api/processDiscount").content("[\n" +
                   "{" +
                   "\"isbn\": 123456666," +
                   "\"quantity\": 2" +
                   "}]").contentType(MediaType.APPLICATION_JSON_VALUE))
           .andExpect(status().isInternalServerError());
    }
}

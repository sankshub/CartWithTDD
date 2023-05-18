package com.sank.bookshop.domain;

import com.sank.bookshop.domain.mappers.RequestResponseMapperImpl;
import com.sank.bookshop.repos.entity.Author;
import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.services.model.DiscountOffer;
import com.sank.bookshop.services.model.Offers;
import com.sank.bookshop.services.model.UniqueBookOffer;
import com.sank.bookshop.services.service.impl.BookServiceImpl;
import com.sank.bookshop.services.service.impl.UniqueSetOfBooks;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private UniqueSetOfBooks discountService;
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
        Book cleanCodeBook = new Book();
        cleanCodeBook.setIsbn("123456789");
        cleanCodeBook.setTitle("Clean Code");
        Author cleanCodeBookAuthor = new Author();
        cleanCodeBookAuthor.setFirstName("Robert");
        cleanCodeBookAuthor.setMiddleName(null);
        cleanCodeBookAuthor.setLastName("Martin");
        cleanCodeBook.setAuthor(cleanCodeBookAuthor);
        cleanCodeBook.setYearOfPublish("2008");
        cleanCodeBook.setPrice("50");

        when(bookService.findAll()).thenReturn(Collections.singletonList(cleanCodeBook));
        when(requestResponseMapper.mapToBookModelList(Mockito.anyList())).thenCallRealMethod();

        String cleanCodeBookJson = "[{\"title\":\"Clean Code\",\"isbn\":\"123456789\",\"authorFullName\":\"Robert Martin\",\"yearOfPublish\":\"2008\",\"price\":\"50\"}]";
        mvc.perform(get("/api/books/"))
           .andExpect(status().isOk())
           .andExpect(content().string(cleanCodeBookJson));
    }

    @Test
    void shouldGetOffersAsJsonResponse() throws Exception {
        DiscountOffer uniqueSetOfBooksOffer;
        List<Offers> offers = new ArrayList<>();
        offers.add(new UniqueBookOffer(2, 5));
        uniqueSetOfBooksOffer = new DiscountOffer("Buy different copies of books to get maximum discount!", offers);
        String cleanCodeBookJson = "{\"offerMessage\":\"Buy different copies of books to get maximum discount!\",\"offers\":[{\"uniqueCopies\":2,\"discount\":\"5\"}]}";

        when(discountService.getCurrentDiscountOffer()).thenReturn(uniqueSetOfBooksOffer);

        mvc.perform(get("/api/discounts/"))
           .andExpect(status().isOk())
           .andExpect(content().string(cleanCodeBookJson));
    }
}

package com.sank.bookshop.domain.controllers;

import com.sank.bookshop.domain.mappers.RequestResponseMapper;
import com.sank.bookshop.domain.model.BookResponse;
import com.sank.bookshop.services.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(value = "/api/")
public class ShoppingController {
    @Autowired
    BookService bookService;

    @GetMapping(value = "books", produces = "application/json")
    public HttpEntity<List<BookResponse>> getAllBooks() {
        return new HttpEntity<>(RequestResponseMapper.MAPPER.mapToBookModelList(bookService.findAll()));
    }

}

package com.sank.bookshop.domain.controllers;

import com.sank.bookshop.repos.entity.Book;
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
    public HttpEntity<List<Book>> getAllBooks() {
        return new HttpEntity<>(bookService.findAll());
    }

}

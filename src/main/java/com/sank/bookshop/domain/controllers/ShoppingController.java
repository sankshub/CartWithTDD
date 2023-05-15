package com.sank.bookshop.domain.controllers;

import com.sank.bookshop.domain.exceptions.ExceptionResponse;
import com.sank.bookshop.domain.mappers.RequestResponseMapper;
import com.sank.bookshop.domain.model.BookResponse;
import com.sank.bookshop.domain.model.CurrentOffer;
import com.sank.bookshop.services.service.BookService;
import com.sank.bookshop.services.service.DiscountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @Autowired
    DiscountService discountService;

    @ApiOperation(value = "Get All Books", notes = "Returns all available books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BookResponse.class),
            @ApiResponse(code = 404, message = "Not found - No books available right now"),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponse.class)})
    @GetMapping(value = "books", produces = "application/json")
    public HttpEntity<List<BookResponse>> getAllBooks() {
        return new HttpEntity<>(RequestResponseMapper.MAPPER.mapToBookModelList(bookService.findAll()));
    }

    @ApiOperation(value = "Get Discount perks", notes = "Returns all available discount rates")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CurrentOffer.class),
            @ApiResponse(code = 404, message = "Not found - No books available right now"),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponse.class)})
    @GetMapping(value = "discounts", produces = "application/json")
    public HttpEntity<CurrentOffer> getCurrentOffers() {
        return new HttpEntity<>(RequestResponseMapper.MAPPER.mapToCurrentOfferModel(discountService.getCurrentDiscountOffer()));
    }

}

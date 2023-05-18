package com.sank.bookshop.controllers;

import com.sank.bookshop.controllers.model.BookResponse;
import com.sank.bookshop.controllers.model.ExceptionResponse;
import com.sank.bookshop.controllers.model.Invoice;
import com.sank.bookshop.controllers.model.ShoppingCart;
import com.sank.bookshop.mappers.RequestResponseMapper;
import com.sank.bookshop.services.BookService;
import com.sank.bookshop.services.DiscountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(code = 200, message = "Success", response = BookResponse[].class),
            @ApiResponse(code = 404, message = "Not found - No books available right now", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponse.class)})
    @GetMapping(value = "books", produces = "application/json")
    public HttpEntity<List<BookResponse>> getAllBooks() {
        return new HttpEntity<>(RequestResponseMapper.MAPPER.mapToBookModelList(bookService.findAll()));
    }

    @ApiOperation(value = "Process shopping cart with possible discount", notes = "Process shopping cart with possible discount and returns")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Invoice.class),
            @ApiResponse(code = 404, message = "Not found - No Book found", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Bad Request - Duplicate entries Found/Minimum quantity required/Cart is Empty", response = ExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal server error", response = ExceptionResponse.class)})
    @PostMapping(value = "processDiscount", consumes = "application/json", produces = "application/json")
    public HttpEntity<Invoice> processDiscount(@RequestBody List<ShoppingCart> cart) {
        com.sank.bookshop.services.model.ShoppingCart orders = RequestResponseMapper.MAPPER.mapToShoppingCartEntity(cart, bookService);
        return new HttpEntity<>(RequestResponseMapper.MAPPER.mapToProcessedCartModel(discountService.applyDiscount(orders)));
    }
}

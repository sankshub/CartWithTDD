package com.sank.bookshop.domain.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"title", "isbn", "authorFullName", "yearOfPublish", "price"})
public class BookResponse {
    private String isbn;
    private String title;
    private String authorFullName;
    private String yearOfPublish;
    private String price;
}

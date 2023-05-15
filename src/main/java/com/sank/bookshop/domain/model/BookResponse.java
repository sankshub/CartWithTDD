package com.sank.bookshop.domain.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonPropertyOrder({"title", "isbn", "authorFullName", "yearOfPublish", "price"})
public class BookResponse {
    @ApiModelProperty(position = 1, example = "123456789123456789")
    private String isbn;
    @ApiModelProperty(example = "Book Title")
    private String title;
    @ApiModelProperty(position = 2, example = "Its Author who wrote")
    private String authorFullName;
    @ApiModelProperty(position = 3, example = "1789")
    private String yearOfPublish;
    @ApiModelProperty(position = 4, example = "150€")
    private String price;
}

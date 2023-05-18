package com.sank.bookshop.services.model;

import com.sank.bookshop.exception.BookNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum Book {
    CLEAN_CODE("12345", "Clean Code", "Robert Martin", "2008", "50"),
    CLEAN_CODER("23451", "The Clean Coder", "Robert Martin", "2011", "50"),
    CLEAN_ARCHITECTURE("34512", "Clean Architecture", "Robert Martin", "2017", "50"),
    TEST_DRIVEN_DEVELOPMENT("45123", "Test Driven Development by Example", "Kent Beck", "2003", "50"),
    LEGACY_CODE("51234", "Working Effectively With Legacy Code", "Michael C. Feathers", "2004", "50");
    final String isbn;
    final String title;
    final String authorName;
    final String yearOfPublish;
    final String price;

    public static Book findBookByISBN(String isbn) {
        return Arrays.stream(Book.values())
                     .filter(p -> Objects.equals(p.getIsbn(), isbn))
                     .findAny()
                     .orElseThrow(() -> new BookNotFoundException(Constants.BOOK_NOT_FOUND_ERROR));
    }
}

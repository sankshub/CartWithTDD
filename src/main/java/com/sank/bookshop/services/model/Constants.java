package com.sank.bookshop.services.model;

public interface Constants {
    String EMPTY_CART_ERROR = "Cart is Empty, add items and request again";
    String MINIMUM_BOOK_QUANTITY_ERROR = "Minimum 1 quantity required per order Check and request again";
    String DUPLICATE_BOOK_ENTRY_ERROR = "We found duplicate orders for ISBN {}, Remove it and request again";
    String BOOK_NOT_FOUND_ERROR = "Requested book not found/ISBN is null, Try again with valid ISBN ";
}

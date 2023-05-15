package com.sank.bookshop.services.service.impl;

import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.services.service.DiscountService;
import com.sank.bookshop.services.service.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.sank.bookshop.services.validator.UniqueSetDiscountValidator.validateShoppingCart;

@Service
public class UniqueSetOfBooks implements DiscountService {
    private final DiscountOffer uniqueSetOfBooksOffer;

    private UniqueSetOfBooks() {
        List<Offers> offers = new ArrayList<>();
        offers.add(new UniqueBookOffer(2, 5));
        offers.add(new UniqueBookOffer(3, 10));
        offers.add(new UniqueBookOffer(4, 20));
        offers.add(new UniqueBookOffer(5, 25));
        uniqueSetOfBooksOffer = new DiscountOffer("Buy different copies of books to get maximum discount!", offers);
    }

    @Override
    public DiscountOffer getCurrentDiscountOffer() {
        return uniqueSetOfBooksOffer;
    }

    @Override
    public DiscountedCart applyDiscount(List<ShoppingCart> shoppingOrderList) {
        validateShoppingCart(shoppingOrderList);
        Set<Book> bookSet = new HashSet<>();
        bookSet.add(shoppingOrderList.get(0).getBook());
        UniqueBasket uniqueBasket = new UniqueBasket(bookSet, 5);
        return new DiscountedCart(Collections.singletonList(uniqueBasket), 50.0, 50.0);
    }
}

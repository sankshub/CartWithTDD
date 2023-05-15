package com.sank.bookshop.services.service.impl;

import com.sank.bookshop.services.service.DiscountService;
import com.sank.bookshop.services.service.model.DiscountOffer;
import com.sank.bookshop.services.service.model.Offers;
import com.sank.bookshop.services.service.model.UniqueBookOffer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}

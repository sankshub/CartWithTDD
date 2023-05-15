package com.sank.bookshop.services.service;

import com.sank.bookshop.services.service.model.DiscountOffer;
import com.sank.bookshop.services.service.model.DiscountedCart;
import com.sank.bookshop.services.service.model.ShoppingCart;

import java.util.List;

public interface DiscountService {
    DiscountOffer getCurrentDiscountOffer();

    DiscountedCart applyDiscount(List<ShoppingCart> shoppingOrderList);
}

package com.sank.bookshop.services.service;

import com.sank.bookshop.services.model.DiscountOffer;
import com.sank.bookshop.services.model.DiscountedCart;
import com.sank.bookshop.services.model.ShoppingCart;

public interface DiscountService {
    DiscountOffer getCurrentDiscountOffer();

    DiscountedCart applyDiscount(ShoppingCart shoppingOrderList);
}

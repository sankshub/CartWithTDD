package com.sank.bookshop.services.service;

import com.sank.bookshop.services.service.model.DiscountOffer;
import com.sank.bookshop.services.service.model.DiscountedCart;
import com.sank.bookshop.services.service.model.ShoppingCart;

public interface DiscountService {
    DiscountOffer getCurrentDiscountOffer();

    DiscountedCart applyDiscount(ShoppingCart shoppingOrderList);
}

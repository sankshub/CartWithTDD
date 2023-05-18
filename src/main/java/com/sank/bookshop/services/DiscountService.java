package com.sank.bookshop.services;

import com.sank.bookshop.services.model.Invoice;
import com.sank.bookshop.services.model.ShoppingCart;

public interface DiscountService {
    Invoice applyDiscount(ShoppingCart shoppingOrderList);
}

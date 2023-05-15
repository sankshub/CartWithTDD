package com.sank.bookshop.services.validator;

import com.sank.bookshop.services.exception.ShoppingCartException;
import com.sank.bookshop.services.service.model.ShoppingCart;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class UniqueSetDiscountValidator {
    private static final String EMPTY_CART_ERROR = "Cart is Empty, add items and request again";

    private UniqueSetDiscountValidator() {
    }

    public static void validateShoppingCart(List<ShoppingCart> shoppingOrderList) throws ShoppingCartException {
        checkCartItems(shoppingOrderList);
    }

    private static void checkCartItems(List<ShoppingCart> shoppingOrderList) throws ShoppingCartException {
        if (CollectionUtils.isEmpty(shoppingOrderList))
            throw new ShoppingCartException(EMPTY_CART_ERROR);

    }
}

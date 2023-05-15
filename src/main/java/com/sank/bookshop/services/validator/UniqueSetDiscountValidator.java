package com.sank.bookshop.services.validator;

import com.sank.bookshop.services.exception.ShoppingCartException;
import com.sank.bookshop.services.service.model.ShoppingCart;
import org.springframework.util.CollectionUtils;

public class UniqueSetDiscountValidator {
    private static final String EMPTY_CART_ERROR = "Cart is Empty, add items and request again";
    private static final String MINIMUM_BOOK_QUANTITY_ERROR = "Minimum 1 quantity required per order Check and request again";

    private UniqueSetDiscountValidator() {
    }

    public static void validateShoppingCart(ShoppingCart shoppingCart) throws ShoppingCartException {
        checkCartItems(shoppingCart);
        checkMinQuantityPerOrder(shoppingCart);
    }

    private static void checkCartItems(ShoppingCart shoppingCart) throws ShoppingCartException {
        if (shoppingCart == null || CollectionUtils.isEmpty(shoppingCart.getItems()))
            throw new ShoppingCartException(EMPTY_CART_ERROR);

    }

    private static void checkMinQuantityPerOrder(ShoppingCart shoppingCart) throws ShoppingCartException {
        if (shoppingCart.getItems().stream()
                        .anyMatch(book -> book.getQuantity() == null || book.getQuantity() < 1))
            throw new ShoppingCartException(MINIMUM_BOOK_QUANTITY_ERROR);

    }
}

package com.sank.bookshop.services.validator;

import com.sank.bookshop.services.exception.ShoppingCartException;
import com.sank.bookshop.services.model.CartItem;
import com.sank.bookshop.services.model.ShoppingCart;
import org.springframework.util.CollectionUtils;

import java.util.function.Function;
import java.util.stream.Collectors;

public class UniqueSetDiscountValidator {
    public static final String EMPTY_CART_ERROR = "Cart is Empty, add items and request again";
    public static final String MINIMUM_BOOK_QUANTITY_ERROR = "Minimum 1 quantity required per order Check and request again";
    public static final String DUPLICATE_BOOK_ENTRY_ERROR = "Duplicate book entry found in Cart, Remove it and request again";

    private UniqueSetDiscountValidator() {
    }

    public static void validateShoppingCart(ShoppingCart shoppingCart) throws ShoppingCartException {
        checkCartItems(shoppingCart);
        checkMinQuantityPerOrder(shoppingCart);
        checkDuplicateItemsInCart(shoppingCart);
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

    private static void checkDuplicateItemsInCart(ShoppingCart shoppingCart) throws ShoppingCartException {
        boolean isDuplicateBookFound = shoppingCart.getItems().stream()
                                                   .map(CartItem::getBook)
                                                   .collect(Collectors.toList())
                                                   .stream()
                                                   .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                                   .entrySet()
                                                   .stream()
                                                   .anyMatch(m -> m.getValue() > 1);
        if (isDuplicateBookFound) throw new ShoppingCartException(DUPLICATE_BOOK_ENTRY_ERROR);
    }
}

package com.sank.bookshop.validator;

import com.sank.bookshop.exception.ShoppingCartException;
import com.sank.bookshop.services.model.Book;
import com.sank.bookshop.services.model.CartItem;
import com.sank.bookshop.services.model.ShoppingCart;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sank.bookshop.services.model.Constants.*;

public class UniqueSetDiscountValidator {

    private UniqueSetDiscountValidator() {
    }

    public static void validateShoppingCart(ShoppingCart shoppingCart) throws ShoppingCartException {
        checkForEmptyCart(shoppingCart);
        checkMinQuantityPerOrder(shoppingCart);
        checkDuplicateItemsInCart(shoppingCart);
    }

    private static void checkForEmptyCart(ShoppingCart shoppingCart) throws ShoppingCartException {
        if (shoppingCart == null || CollectionUtils.isEmpty(shoppingCart.getItems()))
            throw new ShoppingCartException(EMPTY_CART_ERROR);

    }

    private static void checkMinQuantityPerOrder(ShoppingCart shoppingCart) throws ShoppingCartException {
        if (shoppingCart.getItems().stream()
                        .anyMatch(book -> book.getQuantity() == null || book.getQuantity() < 1))
            throw new ShoppingCartException(MINIMUM_BOOK_QUANTITY_ERROR);

    }

    private static void checkDuplicateItemsInCart(ShoppingCart shoppingCart) throws ShoppingCartException {
        String duplicateIsbns = shoppingCart.getItems().stream()
                                            .map(CartItem::getBook)
                                            .collect(Collectors.toList())
                                            .stream()
                                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                                            .entrySet()
                                            .stream()
                                            .filter(m -> m.getValue() > 1).map(Map.Entry::getKey).map(Book::getIsbn)
                                            .collect(Collectors.joining(","));
        if (StringUtils.isNotBlank(duplicateIsbns))
            throw new ShoppingCartException(DUPLICATE_BOOK_ENTRY_ERROR.replace("{}", duplicateIsbns));
    }
}

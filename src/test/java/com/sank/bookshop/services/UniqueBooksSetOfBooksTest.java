package com.sank.bookshop.services;

import com.sank.bookshop.exception.ShoppingCartException;
import com.sank.bookshop.services.impl.UniqueSetOfBooksDiscount;
import com.sank.bookshop.services.model.Book;
import com.sank.bookshop.services.model.CartItem;
import com.sank.bookshop.services.model.Invoice;
import com.sank.bookshop.services.model.ShoppingCart;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sank.bookshop.services.model.Constants.*;

@RunWith(MockitoJUnitRunner.class)
class UniqueBooksSetOfBooksTest {

    UniqueSetOfBooksDiscount uniqueSetOfBooksDiscount = new UniqueSetOfBooksDiscount();

    @Test
    void getSimpleDiscountedCart() {
        CartItem cartItem = new CartItem(Book.CLEAN_CODE, 1);
        ShoppingCart simpleShoppingCart = new ShoppingCart(Collections.singletonList(cartItem));
        Invoice invoice = uniqueSetOfBooksDiscount.applyDiscount(simpleShoppingCart);
        Assert.assertEquals(1, invoice.getUniqueBooksSet().size());
        Assert.assertEquals(1, invoice.getUniqueBooksSet().get(0).getBooks().size());
        Assert.assertTrue(invoice.getUniqueBooksSet().get(0).getBooks().contains(Book.CLEAN_CODE));
    }

    @Test
    void shoppingCartShouldNotEmpty() {
        Exception emptyCartException = Assert.assertThrows(ShoppingCartException.class, () -> {
            uniqueSetOfBooksDiscount.applyDiscount(null);
        });
        Assert.assertEquals(EMPTY_CART_ERROR, emptyCartException.getMessage());

        ShoppingCart cartWithNoItems = new ShoppingCart(Collections.emptyList());
        Exception emptyItemsException = Assert.assertThrows(ShoppingCartException.class, () -> {
            uniqueSetOfBooksDiscount.applyDiscount(cartWithNoItems);
        });
        Assert.assertEquals(EMPTY_CART_ERROR, emptyItemsException.getMessage());
    }

    @Test
    void shoppingCartShouldNotHaveAnyOrderWithZeroBooks() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(Book.CLEAN_CODE, 2));
        items.add(new CartItem(Book.LEGACY_CODE, 0));
        ShoppingCart cartWithoutQuantity = new ShoppingCart(items);

        Exception exception = Assert.assertThrows(ShoppingCartException.class, () -> {
            uniqueSetOfBooksDiscount.applyDiscount(cartWithoutQuantity);
        });
        Assert.assertEquals(MINIMUM_BOOK_QUANTITY_ERROR, exception.getMessage());
    }

    @Test
    void shoppingCartShouldNotHaveAnyDuplicateBooks() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(Book.CLEAN_CODE, 2));
        items.add(new CartItem(Book.CLEAN_CODE, 2));
        ShoppingCart cartWithDuplicates = new ShoppingCart(items);
        Exception exception = Assert.assertThrows(ShoppingCartException.class, () -> {
            uniqueSetOfBooksDiscount.applyDiscount(cartWithDuplicates);
        });
        Assert.assertEquals(DUPLICATE_BOOK_ENTRY_ERROR.replace("{}", Book.CLEAN_CODE.getIsbn()), exception.getMessage());
    }

    @Test
    void shoppingCartSegregateCartItemsIntoBaskets() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(Book.CLEAN_CODE, 2));
        items.add(new CartItem(Book.LEGACY_CODE, 2));
        ShoppingCart shoppingCart = new ShoppingCart(items);
        Invoice invoice = uniqueSetOfBooksDiscount.applyDiscount(shoppingCart);
        Assert.assertEquals(2, invoice.getUniqueBooksSet().size());
    }

    @Test
    void getBestDiscount() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(Book.CLEAN_CODE, 2));
        items.add(new CartItem(Book.LEGACY_CODE, 2));
        items.add(new CartItem(Book.TEST_DRIVEN_DEVELOPMENT, 2));
        items.add(new CartItem(Book.CLEAN_ARCHITECTURE, 1));
        items.add(new CartItem(Book.CLEAN_CODER, 1));
        ShoppingCart shoppingCart = new ShoppingCart(items);
        Invoice invoice = uniqueSetOfBooksDiscount.applyDiscount(shoppingCart);
        Assert.assertEquals(2, invoice.getUniqueBooksSet().size());
        Assert.assertEquals(4, invoice.getUniqueBooksSet().get(0).getBooks().size());
        Assert.assertEquals(Double.valueOf(320.0), invoice.getTotalCostWithDiscount());
        Assert.assertEquals(Double.valueOf(400.0), invoice.getTotalCost());
    }
}
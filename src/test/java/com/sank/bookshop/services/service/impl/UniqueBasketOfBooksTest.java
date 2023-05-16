package com.sank.bookshop.services.service.impl;

import com.sank.bookshop.repos.entity.Author;
import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.services.exception.ShoppingCartException;
import com.sank.bookshop.services.model.CartItem;
import com.sank.bookshop.services.model.DiscountedCart;
import com.sank.bookshop.services.model.ShoppingCart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.sank.bookshop.services.validator.UniqueSetDiscountValidator.*;

@RunWith(MockitoJUnitRunner.class)
public class UniqueBasketOfBooksTest {
    Book cleanCodeBook = new Book();
    Book legacyCodeBook = new Book();
    @InjectMocks
    UniqueSetOfBooks uniqueSetOfBooks;

    @Before
    public void setup() throws Exception {
        cleanCodeBook.setIsbn("123456789");
        cleanCodeBook.setTitle("Clean Code");
        Author author = new Author();
        author.setFirstName("Robert");
        author.setMiddleName(null);
        author.setLastName("Martin");
        cleanCodeBook.setAuthor(author);
        cleanCodeBook.setYearOfPublish("2008");
        cleanCodeBook.setPrice("50.0");

        legacyCodeBook.setIsbn("567891234");
        legacyCodeBook.setTitle("Working Effectively With Legacy Code");
        Author authorLegacyCode = new Author();
        authorLegacyCode.setFirstName("Michael");
        authorLegacyCode.setMiddleName("C.");
        authorLegacyCode.setLastName("Feathers");
        legacyCodeBook.setAuthor(authorLegacyCode);
        legacyCodeBook.setYearOfPublish("2004");
        legacyCodeBook.setPrice("60.0");
    }

    @Test
    public void getSimpleDiscountedCart() {
        CartItem cartItem = new CartItem(cleanCodeBook, 1);
        ShoppingCart simpleShoppingCart = new ShoppingCart(Collections.singletonList(cartItem));
        DiscountedCart discountedCart = uniqueSetOfBooks.applyDiscount(simpleShoppingCart);
        Assert.assertEquals(1, discountedCart.getUniqueBasket().size());
        Assert.assertEquals(1, discountedCart.getUniqueBasket()
                                             .get(0)
                                             .getBooks()
                                             .size());
        Assert.assertTrue(discountedCart.getUniqueBasket()
                                        .get(0)
                                        .getBooks()
                                        .contains(cleanCodeBook));
    }

    @Test
    public void shoppingCartShouldNotEmpty() {
        Exception emptyCartException = Assert.assertThrows(ShoppingCartException.class, () -> {
            uniqueSetOfBooks.applyDiscount(null);
        });
        Assert.assertEquals(EMPTY_CART_ERROR, emptyCartException.getMessage());

        ShoppingCart cartWithNoItems = new ShoppingCart(Collections.emptyList());
        Exception emptyItemsException = Assert.assertThrows(ShoppingCartException.class, () -> {
            uniqueSetOfBooks.applyDiscount(cartWithNoItems);
        });
        Assert.assertEquals(EMPTY_CART_ERROR, emptyItemsException.getMessage());
    }

    @Test
    public void shoppingCartShouldNotHaveAnyOrderWithZeroBooks() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(cleanCodeBook, 2));
        items.add(new CartItem(legacyCodeBook, 0));
        ShoppingCart cartWithoutQuantity = new ShoppingCart(items);

        Exception exception = Assert.assertThrows(ShoppingCartException.class, () -> {
            uniqueSetOfBooks.applyDiscount(cartWithoutQuantity);
        });
        Assert.assertEquals(MINIMUM_BOOK_QUANTITY_ERROR, exception.getMessage());
    }

    @Test
    public void shoppingCartShouldNotHaveAnyDuplicateBooks() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(cleanCodeBook, 2));
        items.add(new CartItem(cleanCodeBook, 2));
        ShoppingCart cartWithDuplicates = new ShoppingCart(items);
        Exception exception = Assert.assertThrows(ShoppingCartException.class, () -> {
            uniqueSetOfBooks.applyDiscount(cartWithDuplicates);
        });
        Assert.assertEquals(DUPLICATE_BOOK_ENTRY_ERROR, exception.getMessage());
    }

    @Test
    public void shoppingCartSegricateCartItemsIntoBaskets() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(cleanCodeBook, 2));
        items.add(new CartItem(legacyCodeBook, 2));
        ShoppingCart shoppingCart = new ShoppingCart(items);
        DiscountedCart discountedCart = uniqueSetOfBooks.applyDiscount(shoppingCart);
        Assert.assertEquals(2, discountedCart.getUniqueBasket().size());
    }
}
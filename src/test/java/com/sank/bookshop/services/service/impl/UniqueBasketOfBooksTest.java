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
    Book theCleanCoderBook = new Book();
    Book cleanArchitectureBook = new Book();
    Book testDrivenDevelopmentBook = new Book();
    @InjectMocks
    UniqueSetOfBooks uniqueSetOfBooks;

    @Before
    public void setup() throws Exception {
        Author authorRobert = new Author();
        authorRobert.setFirstName("Robert");
        authorRobert.setMiddleName(null);
        authorRobert.setLastName("Martin");

        Author authorKent = new Author();
        authorKent.setFirstName("Kent");
        authorKent.setMiddleName(null);
        authorKent.setLastName("Beck");

        Author authorMichael = new Author();
        authorMichael.setFirstName("Michael");
        authorMichael.setMiddleName("C.");
        authorMichael.setLastName("Feathers");

        cleanCodeBook.setIsbn("123456789");
        cleanCodeBook.setTitle("Clean Code");
        cleanCodeBook.setAuthor(authorRobert);
        cleanCodeBook.setYearOfPublish("2008");
        cleanCodeBook.setPrice("50.0");

        legacyCodeBook.setIsbn("567891234");
        legacyCodeBook.setTitle("Working Effectively With Legacy Code");
        legacyCodeBook.setAuthor(authorMichael);
        legacyCodeBook.setYearOfPublish("2004");
        legacyCodeBook.setPrice("50.0");

        theCleanCoderBook.setIsbn("234567891");
        theCleanCoderBook.setTitle("The Clean Coder");
        theCleanCoderBook.setAuthor(authorRobert);
        theCleanCoderBook.setYearOfPublish("2011");
        theCleanCoderBook.setPrice("50.0");

        cleanArchitectureBook.setIsbn("345678912");
        cleanArchitectureBook.setTitle("Clean Architecture");
        cleanArchitectureBook.setAuthor(authorRobert);
        cleanArchitectureBook.setYearOfPublish("2017");
        cleanArchitectureBook.setPrice("50.0");

        testDrivenDevelopmentBook.setIsbn("456789123");
        testDrivenDevelopmentBook.setTitle("Test Driven Development by Example");
        testDrivenDevelopmentBook.setAuthor(authorKent);
        testDrivenDevelopmentBook.setYearOfPublish("2003");
        testDrivenDevelopmentBook.setPrice("50.0");
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
    public void shoppingCartSegregateCartItemsIntoBaskets() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(cleanCodeBook, 2));
        items.add(new CartItem(legacyCodeBook, 2));
        ShoppingCart shoppingCart = new ShoppingCart(items);
        DiscountedCart discountedCart = uniqueSetOfBooks.applyDiscount(shoppingCart);
        Assert.assertEquals(2, discountedCart.getUniqueBasket().size());
    }

    @Test
    public void getBestDiscount() {
        List<CartItem> items = new ArrayList<>();
        items.add(new CartItem(cleanCodeBook, 2));
        items.add(new CartItem(legacyCodeBook, 2));
        items.add(new CartItem(cleanArchitectureBook, 2));
        items.add(new CartItem(testDrivenDevelopmentBook, 1));
        items.add(new CartItem(theCleanCoderBook, 1));
        ShoppingCart shoppingCart = new ShoppingCart(items);
        DiscountedCart discountedCart = uniqueSetOfBooks.applyDiscount(shoppingCart);
        Assert.assertEquals(2, discountedCart.getUniqueBasket().size());
        Assert.assertEquals(4, discountedCart.getUniqueBasket().get(0).getBooks().size());
        Assert.assertEquals(Double.valueOf(320.0), discountedCart.getTotalCostWithDiscount());
        Assert.assertEquals(Double.valueOf(400.0), discountedCart.getTotalCost());
    }
}
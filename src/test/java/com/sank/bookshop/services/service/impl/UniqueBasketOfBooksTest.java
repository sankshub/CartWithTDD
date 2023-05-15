package com.sank.bookshop.services.service.impl;

import com.sank.bookshop.repos.entity.Author;
import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.services.exception.ShoppingCartException;
import com.sank.bookshop.services.service.model.DiscountedCart;
import com.sank.bookshop.services.service.model.ShoppingCart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class UniqueBasketOfBooksTest {
    Book cleanCodeBook = new Book();
    private static final String EMPTY_CART_ERROR = "Cart is Empty, add items and request again";
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
    }

    @Test
    public void getSimpleDiscountedCart() {
        ShoppingCart simpleShoppingCart = new ShoppingCart(cleanCodeBook, 1);
        DiscountedCart discountedCart = uniqueSetOfBooks.applyDiscount(Collections.singletonList(simpleShoppingCart));
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
        Exception exception = Assert.assertThrows(ShoppingCartException.class, () -> {
            uniqueSetOfBooks.applyDiscount(null);
        });
        Assert.assertEquals(EMPTY_CART_ERROR, exception.getMessage());
    }
}
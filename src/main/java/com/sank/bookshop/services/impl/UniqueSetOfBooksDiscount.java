package com.sank.bookshop.services.impl;

import com.sank.bookshop.services.DiscountService;
import com.sank.bookshop.services.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.sank.bookshop.validator.UniqueSetDiscountValidator.validateShoppingCart;

@Service
public class UniqueSetOfBooksDiscount implements DiscountService {
    @Override
    public Invoice applyDiscount(ShoppingCart shoppingCart) {
        validateShoppingCart(shoppingCart);
        return selectInvoiceWithMaxDiscount(processIntoPossibleWaysTheBooksCanBeInvoiced(shoppingCart));
    }

    private List<PossibleGroupingsOfBooksForDiscount> processIntoPossibleWaysTheBooksCanBeInvoiced(ShoppingCart shoppingCart) {
        List<PossibleGroupingsOfBooksForDiscount> possibleWays = new ArrayList<>();
        for (Integer maxNumberOfBooksInAUniqueSet = shoppingCart.getItems()
                                                                .size(); maxNumberOfBooksInAUniqueSet >= UniqueBookSetOffer.DEFAULT.getUniqueCopies(); maxNumberOfBooksInAUniqueSet--) {
            possibleWays.add(createPossibleUniqueSetsOfBooks(shoppingCart, maxNumberOfBooksInAUniqueSet));
        }
        return possibleWays;
    }

    private PossibleGroupingsOfBooksForDiscount createPossibleUniqueSetsOfBooks(ShoppingCart shoppingCart, Integer maxNumberOfBooksInAUniqueSet) {
        List<UniqueBooksSet> uniqueBooksSets = new ArrayList<>();
        List<CartItem> clonedShoppingCartItems = cloneShoppingCartItems(shoppingCart);
        while (!clonedShoppingCartItems.isEmpty()) {
            maxNumberOfBooksInAUniqueSet = maxNumberOfBooksInAUniqueSet > clonedShoppingCartItems.size() ? clonedShoppingCartItems.size() : maxNumberOfBooksInAUniqueSet;
            Set<Book> uniqueSet = createNextSet(clonedShoppingCartItems, maxNumberOfBooksInAUniqueSet);
            uniqueBooksSets.add(new UniqueBooksSet(uniqueSet, getDiscount(uniqueSet.size())));
        }
        return new PossibleGroupingsOfBooksForDiscount(uniqueBooksSets);
    }

    private List<CartItem> cloneShoppingCartItems(ShoppingCart shoppingCart) {
        return shoppingCart.getItems().stream()
                           .map(shoppingOrder -> new CartItem(shoppingOrder.getBook(), shoppingOrder.getQuantity()))
                           .collect(Collectors.toList());
    }

    private Set<Book> createNextSet(List<CartItem> remainingCartItems, Integer maxSetSize) {
        HashSet<Book> books = new HashSet<>();
        for (CartItem item : new ArrayList<>(remainingCartItems)) {
            books.add(item.getBook());
            if (item.getQuantity() == 1)
                remainingCartItems.remove(item);
            else
                item.changeQuantity(item.getQuantity() - 1);
            if (books.size() == maxSetSize)
                break;
        }
        return books;
    }

    private Integer getDiscount(Integer noOfBooksInUniqueSet) {
        return UniqueBookSetOffer.findDiscountByNumberOfBookInASet(noOfBooksInUniqueSet).getDiscount();
    }

    private Invoice selectInvoiceWithMaxDiscount(List<PossibleGroupingsOfBooksForDiscount> possibleGroupingsOfBooksForDiscounts) {
        TreeMap<Double, Invoice> combinationHashMap = new TreeMap<>();
        for (PossibleGroupingsOfBooksForDiscount possibleGroupingsOfBooksForDiscount : possibleGroupingsOfBooksForDiscounts) {
            Invoice invoice = calculateDiscountAndGetDiscountedCart(possibleGroupingsOfBooksForDiscount);
            combinationHashMap.put(invoice.getTotalCostWithDiscount(), invoice);
        }
        return combinationHashMap.firstEntry().getValue();
    }

    private Invoice calculateDiscountAndGetDiscountedCart(PossibleGroupingsOfBooksForDiscount bundle) {
        double pricePerBasket = 0.0;
        double totalPriceOfBundle = 0.0;
        double totalPriceOfBundleWithDiscount = 0.0;
        for (UniqueBooksSet basket : bundle.getUniqueBooksSets()) {
            for (Book book : basket.getBooks()) {
                pricePerBasket += Double.parseDouble(book.getPrice());
                totalPriceOfBundle += Double.parseDouble(book.getPrice());
            }
            pricePerBasket = pricePerBasket * (1.0 - (basket.getDiscountApplied() / 100.0));
            basket.setCostOfBookSetWithDiscount(pricePerBasket);
            totalPriceOfBundleWithDiscount += pricePerBasket;
            pricePerBasket = 0;
        }
        return new Invoice(bundle.getUniqueBooksSets(), totalPriceOfBundle, totalPriceOfBundleWithDiscount);
    }

}

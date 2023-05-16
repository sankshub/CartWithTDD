package com.sank.bookshop.services.service.impl;

import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.services.model.*;
import com.sank.bookshop.services.service.DiscountService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.sank.bookshop.services.validator.UniqueSetDiscountValidator.validateShoppingCart;

@Service
public class UniqueSetOfBooks implements DiscountService {
    private static final Integer MINIMUM_BOOK_SET_SIZE = 1;
    private final DiscountOffer uniqueSetOfBooksOffer;

    private UniqueSetOfBooks() {
        List<Offers> offers = new ArrayList<>();
        offers.add(new UniqueBookOffer(2, 5));
        offers.add(new UniqueBookOffer(3, 10));
        offers.add(new UniqueBookOffer(4, 20));
        offers.add(new UniqueBookOffer(5, 25));
        uniqueSetOfBooksOffer = new DiscountOffer("Buy different copies of books to get maximum discount!", offers);
    }

    @Override
    public DiscountOffer getCurrentDiscountOffer() {
        return uniqueSetOfBooksOffer;
    }

    @Override
    public DiscountedCart applyDiscount(ShoppingCart shoppingCart) {
        validateShoppingCart(shoppingCart);
        return selectBasketBundleWithMaxDiscount(processCartIntoBaskets(shoppingCart));
    }

    private List<BasketBundle> processCartIntoBaskets(ShoppingCart shoppingCart) {
        List<BasketBundle> bundleBaskets = new ArrayList<>();
        for (Integer currentBasketSize : getMaxBooksInAllCatageoriesToGetDiscount().descendingSet()) {
            bundleBaskets.add(new BasketBundle(createPossibleBaskets(shoppingCart, currentBasketSize)));
        }
        return bundleBaskets;
    }

    private TreeSet<Integer> getMaxBooksInAllCatageoriesToGetDiscount() {
        return uniqueSetOfBooksOffer.getOffers().stream().map(item -> ((UniqueBookOffer) item).getUniqueCopies())
                                    .collect(Collectors.toCollection(TreeSet::new));
    }

    private List<UniqueBasket> createPossibleBaskets(ShoppingCart shoppingCart, Integer maxBasketSize) {
        List<UniqueBasket> baskets = new ArrayList<>();
        List<CartItem> remainingShoppingCartItems = cloneShoppingCartItems(shoppingCart);
        TreeSet<Integer> maxSetSize = getMaxBooksInAllCatageoriesToGetDiscount();
        maxSetSize.add(MINIMUM_BOOK_SET_SIZE);
        while (!remainingShoppingCartItems.isEmpty()) {
            maxSetSize.removeIf(integer -> integer > remainingShoppingCartItems.size() || integer > maxBasketSize);
            Set<Book> setsOfDifferentBooks = createNextSet(remainingShoppingCartItems, maxSetSize.last());
            baskets.add(new UniqueBasket(setsOfDifferentBooks, getDiscount(setsOfDifferentBooks.size())));
        }
        return baskets;
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

    private int getDiscount(int differentBooksCount) {
        int defaultDiscount = 0;
        Optional<Offers> bookOffer = uniqueSetOfBooksOffer.getOffers().stream()
                                                          .filter(offer -> ((UniqueBookOffer) offer).getUniqueCopies() == differentBooksCount)
                                                          .findFirst();
        if (bookOffer.isPresent())
            return bookOffer.get().getDiscount();
        else return defaultDiscount;
    }

    private DiscountedCart selectBasketBundleWithMaxDiscount(List<BasketBundle> basketBundles) {
        TreeMap<Double, DiscountedCart> combinationHashMap = new TreeMap<>();
        for (BasketBundle basketBundle : basketBundles) {
            DiscountedCart discountedCart = calculateDiscountAndGetDiscountedCart(basketBundle);
            combinationHashMap.put(discountedCart.getTotalCostWithDiscount(), discountedCart);
        }
        return combinationHashMap.firstEntry().getValue();
    }

    private DiscountedCart calculateDiscountAndGetDiscountedCart(BasketBundle bundle) {
        double pricePerBasket = 0.0;
        double totalPriceOfBundle = 0.0;
        double totalPriceOfBundleWithDiscount = 0.0;
        for (UniqueBasket basket : bundle.getBaskets()) {
            for (Book book : basket.getBooks()) {
                pricePerBasket += Double.parseDouble(book.getPrice());
                totalPriceOfBundle += Double.parseDouble(book.getPrice());
            }
            pricePerBasket = pricePerBasket * (1.0 - (basket.getDiscountApplied() / 100.0));
            basket.setCostOfBasketWithDiscount(pricePerBasket);
            totalPriceOfBundleWithDiscount += pricePerBasket;
            pricePerBasket = 0;
        }
        return new DiscountedCart(bundle.getBaskets(), totalPriceOfBundle, totalPriceOfBundleWithDiscount);
    }

}

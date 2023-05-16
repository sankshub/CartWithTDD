package com.sank.bookshop.domain.mappers;

import com.sank.bookshop.domain.model.Offers;
import com.sank.bookshop.domain.model.*;
import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.services.model.ShoppingCart;
import com.sank.bookshop.services.model.*;
import com.sank.bookshop.services.service.BookService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestResponseMapper {
    RequestResponseMapper MAPPER = Mappers.getMapper(RequestResponseMapper.class);

    List<BookResponse> mapToBookModelList(List<Book> source);

    @Mapping(target = "authorFullName", expression = "java(source.getAuthor().getFullName())")
    BookResponse mapBookModel(Book source);

    CurrentOffer mapToCurrentOfferModel(DiscountOffer source);

    @Mapping(target = "uniqueCopies", expression = "java(((com.sank.bookshop.services.model.UniqueBookOffer) source).getUniqueCopies())")
    Offers mapToOffersModel(com.sank.bookshop.services.model.Offers source);

    default ShoppingCart mapToShoppingCartEntity(List<com.sank.bookshop.domain.model.ShoppingCart> source, BookService bookService) {
        List<CartItem> cartItems = new ArrayList<>();
        for (com.sank.bookshop.domain.model.ShoppingCart cart : source) {
            Book entity = bookService.findByIsbn(cart.getIsbn());
            cartItems.add(new CartItem(entity, cart.getQuantity()));
        }
        return new ShoppingCart(cartItems);
    }

    @Mapping(target = "totalCost", expression = "java(source.getTotalCost() + \"€\")")
    @Mapping(target = "totalCostWithDiscount", expression = "java(source.getTotalCostWithDiscount() + \"€\")")
    @Mapping(target = "basket", source = "uniqueBasket")
    ProcessedCart mapToProcessedCartModel(DiscountedCart source);

    List<Basket> mapToBasketModelList(List<UniqueBasket> source);

    @Mapping(target = "discountApplied", expression = "java(source.getDiscountApplied() + \"%\")")
    @Mapping(target = "totalCostOfBasket", expression = "java(String.valueOf(source.getBooks().stream().filter(java.util.Objects::nonNull).mapToDouble(value -> Double.parseDouble(value.getPrice())).sum()) + \"€\")")
    @Mapping(target = "totalCostOfBasketWithDiscount", expression = "java(source.getCostOfBasketWithDiscount() + \"€\")")
    Basket mapToBasketModel(UniqueBasket source);
}

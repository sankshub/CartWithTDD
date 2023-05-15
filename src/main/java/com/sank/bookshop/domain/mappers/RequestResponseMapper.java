package com.sank.bookshop.domain.mappers;

import com.sank.bookshop.domain.model.BookResponse;
import com.sank.bookshop.domain.model.CurrentOffer;
import com.sank.bookshop.domain.model.Offers;
import com.sank.bookshop.repos.entity.Book;
import com.sank.bookshop.services.model.DiscountOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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

}

package com.sank.bookshop.domain.mappers;

import com.sank.bookshop.domain.model.BookResponse;
import com.sank.bookshop.repos.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestResponseMapper {
    RequestResponseMapper MAPPER = Mappers.getMapper(RequestResponseMapper.class);

    List<BookResponse> mapToBookModelList(List<Book> source);

    BookResponse mapBookModel(Book source);

}

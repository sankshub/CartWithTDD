package com.sank.bookshop.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PossibleGroupingsOfBooksForDiscount {
    List<UniqueBooksSet> uniqueBooksSets;
}

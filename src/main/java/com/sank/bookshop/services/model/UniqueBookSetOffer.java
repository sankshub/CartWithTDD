package com.sank.bookshop.services.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum UniqueBookSetOffer {
    DEFAULT(1, 0),
    TWO(2, 5),
    THREE(3, 10),
    FOUR(4, 20),
    FIVE(5, 25);
    final Integer uniqueCopies;
    final Integer discount;

    public static UniqueBookSetOffer findDiscountByNumberOfBookInASet(Integer uniqueCopies) {
        return Arrays.stream(UniqueBookSetOffer.values())
                     .filter(p -> Objects.equals(p.getUniqueCopies(), uniqueCopies))
                     .findAny()
                     .orElse(DEFAULT);
    }
}

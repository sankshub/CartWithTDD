package com.sank.bookshop.services.model;

import lombok.Data;

@Data
public class UniqueBookOffer extends Offers {
    private Integer uniqueCopies;

    public UniqueBookOffer(Integer uniqueCopies, Integer discount){
        this.uniqueCopies = uniqueCopies;
        setDiscount(discount);
    }

    public UniqueBookOffer() {

    }
}

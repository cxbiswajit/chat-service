package com.openprice.chatservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private String productId;
    private String productName;
    private double productListingPrice;
    private double sellerDisagreementPrice;//seller can't go below this price 
    private double buyerDisagreementPrice;//offer price customer didn't like
    private double buyerAskingPrice;//customer asking for lower price
    private double buyerLastAskingPrice;//customer last asking price
    private double askingPriceThreshold;//customer asking for lower price
    private double buyerBargainingPower;//value between 0.7 to 0.75 depending on buyer's history
    private double dealPrice;//final price product got sold 
    private String deal;
    //any other extra offer with this product 
}

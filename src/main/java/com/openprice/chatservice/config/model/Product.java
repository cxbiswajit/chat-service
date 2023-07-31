package com.openprice.chatservice.config.model;

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
    private String product_id;
    private String product_name;
    private Double product_open_price;
    private Double product_close_price;
}

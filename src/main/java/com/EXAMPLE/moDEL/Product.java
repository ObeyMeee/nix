package com.example.model;

import lombok.Data;

@Data
public abstract class Product {
    protected long id;
    protected boolean available;
    protected String title;
    protected double price;
    protected Channel channel;
    protected ProductType productType;

    protected Product(ProductType productType) {
        this.productType = productType;
    }
}
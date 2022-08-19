package com.example.model;

import lombok.Setter;

@Setter
public class ProductBundle extends Product implements ConsumerAmountInBundle {
    protected int amount;

    public ProductBundle() {
        super(ProductType.PRODUCT_BUNDLE);
    }


    @Override
    public int getAmountInBundle() {
        return amount;
    }

    @Override
    public String toString() {
        return "ProductBundle{" +
                "id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", channel='" + channel + '\'' +
                ", amount=" + amount +
                '}';
    }
}
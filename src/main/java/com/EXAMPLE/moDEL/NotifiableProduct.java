package com.example.model;

import lombok.Setter;

@Setter
public class NotifiableProduct extends Product implements AddressGenerator {

    public NotifiableProduct() {
        super(ProductType.NOTIFIABLE_PRODUCT);
    }

    @Override
    public String generateAddressForNotification() {
        return "somerandommail@gmail.com";
    }

    @Override
    public String toString() {
        return "NotifiableProduct{" +
                "id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", channel='" + channel + '\'' +
                '}';
    }
}
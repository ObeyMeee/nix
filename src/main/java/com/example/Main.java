package com.example;

import com.example.model.Product;
import com.example.utils.NotifiableProductUtils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        NotifiableProductUtils notifiableProductUtils = new NotifiableProductUtils();

        List<Product> products = notifiableProductUtils.generateRandomProductList();
        notifiableProductUtils.saveAll(products);
        notifiableProductUtils.print();

        System.out.println("\nNotifications sent: " + notifiableProductUtils.sendNotifications());
    }

}

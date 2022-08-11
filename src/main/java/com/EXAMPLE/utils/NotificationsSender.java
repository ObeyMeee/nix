package com.example.utils;

import com.example.model.Product;

import java.util.List;

public class NotificationsSender implements Sender {
    @Override
    public int send(List<Product> products) {
        // sending notifications...
        return products.size();
    }
}

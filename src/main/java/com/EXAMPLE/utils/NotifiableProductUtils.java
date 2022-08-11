package com.example.utils;

import com.example.model.Product;

import java.util.List;

public class NotifiableProductUtils extends ProductUtils {


    public int sendNotifications() {
        List<Product> products = filterNotifiableProducts();
        NotificationsSender sender = new NotificationsSender();
        return sender.send(products);
    }

    private List<Product> filterNotifiableProducts() {
        return repository.getAll()
                .stream()
                .filter(product -> product.getClass().getSimpleName().equals("NotifiableProduct"))
                .toList();
    }

}

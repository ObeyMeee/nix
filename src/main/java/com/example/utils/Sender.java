package com.example.utils;

import com.example.model.Product;

import java.util.List;

public interface Sender {
    int send(List<Product> products);
}

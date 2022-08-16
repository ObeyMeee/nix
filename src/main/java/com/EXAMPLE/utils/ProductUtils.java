package com.example.utils;

import com.example.model.*;
import com.example.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductUtils {
    protected final ProductRepository repository = new ProductRepository();

    protected static final Random RANDOM = new Random();

    public List<Product> generateRandomProductList() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            products.add(generateRandomProduct());
        }
        return products;
    }

    private Product generateRandomProduct() {
        int productTypesSize = ProductType.values().length;
        return switch (RANDOM.nextInt(productTypesSize)) {
            case 0 -> createRandomNotifiableProduct();
            case 1 -> createRandomProductBundle();
            default -> throw new IllegalStateException("Unexpected value: " + RANDOM.nextInt(productTypesSize));
        };
    }

    private Product createRandomNotifiableProduct() {
        NotifiableProduct notifiableProduct = new NotifiableProduct();
        notifiableProduct.setId(RANDOM.nextLong());
        notifiableProduct.setTitle(RANDOM.nextFloat() + "" + RANDOM.nextDouble());
        notifiableProduct.setAvailable(RANDOM.nextBoolean());
        notifiableProduct.setChannel(new Channel(RANDOM.nextBoolean() + "" + RANDOM.nextDouble()));
        notifiableProduct.setPrice(RANDOM.nextDouble());
        return notifiableProduct;
    }

    private Product createRandomProductBundle() {
        ProductBundle productBundle = new ProductBundle();
        productBundle.setAmount(RANDOM.nextInt(15));
        productBundle.setAvailable(RANDOM.nextBoolean());
        productBundle.setChannel(new Channel(RANDOM.nextBoolean() + "" + RANDOM.nextDouble()));
        productBundle.setPrice(RANDOM.nextDouble());
        productBundle.setId(RANDOM.nextLong());
        productBundle.setTitle(RANDOM.nextFloat() + "" + RANDOM.nextDouble());
        return productBundle;
    }

    public void save(Product product) {
        repository.save(product);
    }

    public void saveAll(List<Product> products) {
        products.forEach(this::save);
    }


    public List<Product> getAll() {
        return repository.getAll();
    }

    public void print() {
        List<Product> products = getAll();
        products.forEach(System.out::println);
    }
}

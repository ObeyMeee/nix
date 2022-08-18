package ua.com.andromeda.module2.service;

import ua.com.andromeda.module2.entity.Customer;

import java.util.Random;

public class PersonService {
    private static PersonService instance;

    private final Random random;


    private PersonService() {
        random = new Random();
    }

    public static PersonService getInstance() {
        if (instance == null) {
            instance = new PersonService();
        }
        return instance;
    }

    public Customer getRandomCustomer() {
        int age = random.nextInt(10, 100);
        String email = "email_" + random.nextInt(1000) + "@gmail.com";
        return new Customer(email, age);
    }
}

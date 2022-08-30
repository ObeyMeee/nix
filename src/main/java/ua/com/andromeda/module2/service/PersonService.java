package ua.com.andromeda.module2.service;

import ua.com.andromeda.homework19.annotations.Autowired;
import ua.com.andromeda.homework19.annotations.Singleton;
import ua.com.andromeda.module2.entity.Customer;

import java.util.Random;

@Singleton
public class PersonService {

    private final Random random;

    @Autowired
    public PersonService() {
        random = new Random();
    }

    public Customer getRandomCustomer() {
        int age = random.nextInt(10, 100);
        String email = "email_" + random.nextInt(1000) + "@gmail.com";
        return new Customer(email, age);
    }
}

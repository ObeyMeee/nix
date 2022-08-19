package ua.com.andromeda.module2.service;

import org.junit.Before;
import org.junit.Test;
import ua.com.andromeda.module2.entity.Customer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PersonServiceTest {
    PersonService target;

    @Before
    public void setUp() {
        target = PersonService.getInstance();
    }

    @Test
    public void getRandomCustomer_hasId() {
        Customer randomCustomer = target.getRandomCustomer();
        assertNotNull(randomCustomer);
        assertNotNull(randomCustomer.getId());
    }

    @Test
    public void getRandomCustomer_hasEmail() {
        Customer randomCustomer = target.getRandomCustomer();
        assertNotNull(randomCustomer);
        assertNotNull(randomCustomer.getEmail());
    }

    @Test
    public void getRandomCustomer_hasAgeHigherOrEqualsTen() {
        Customer randomCustomer = target.getRandomCustomer();
        assertNotNull(randomCustomer);
        assertTrue(randomCustomer.getAge() >= 10);
    }

    @Test
    public void getRandomCustomer_hasAgeLessHundred() {
        Customer randomCustomer = target.getRandomCustomer();
        assertNotNull(randomCustomer);
        assertTrue(randomCustomer.getAge() < 100);
    }
}
package ua.com.andromeda.module2.service;

import org.junit.Before;
import org.junit.Test;
import ua.com.andromeda.module2.entity.Customer;
import ua.com.andromeda.module2.entity.Invoice;
import ua.com.andromeda.module2.entity.InvoiceType;
import ua.com.andromeda.module2.entity.Product;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShopServiceTest {

    ShopService target;

    // reset singleton
    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field instance = ShopService.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        target = ShopService.getInstance();
    }

    @Test
    public void getInvoices_emptyList() {
        List<Invoice> actual = target.getInvoices();
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    public void getInvoices_checkSize() {
        for (int i = 0; i < 10; i++) {
            target.saveInvoice(createSimpleInvoice());
        }
        List<Invoice> invoices = target.getInvoices();
        assertNotNull(invoices);
        assertEquals(10, invoices.size());
    }

    private Invoice createSimpleInvoice() {
        return new Invoice();
    }

    @Test
    public void createRandomInvoice_checkType() {
        Invoice invoice = target.createRandomInvoice(BigDecimal.valueOf(50));
        InvoiceType expected = InvoiceType.WHOLESALE;
        InvoiceType actual = invoice.getType();
        assertEquals(expected, actual);
    }

    @Test
    public void createRandomInvoice_checkCustomer() {
        Invoice invoice = target.createRandomInvoice(BigDecimal.valueOf(50));
        Customer customer = invoice.getCustomer();
        assertNotNull(customer.getEmail());
        assertNotEquals(0, customer.getAge());
    }

    @Test
    public void createRandomInvoice_checkProducts() {
        Invoice invoice = target.createRandomInvoice(BigDecimal.valueOf(50));
        Map<Product, Integer> products = invoice.getProducts();
        assertNotNull(products);
    }

    @Test
    public void saveInvoice_FirstInvoice() {
        target.saveInvoice(createSimpleInvoice());
        List<Invoice> invoices = target.getInvoices();
        assertNotNull(invoices);
        assertEquals(1, invoices.size());
    }

}
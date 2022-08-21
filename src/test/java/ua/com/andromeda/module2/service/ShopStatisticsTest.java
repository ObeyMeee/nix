package ua.com.andromeda.module2.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.andromeda.module2.entity.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ShopStatisticsTest {
    ShopStatistics target;
    ShopService shopService;

    @Before
    public void setUp() {
        shopService = Mockito.mock(ShopService.class);
        target = new ShopStatistics(shopService);
    }

    private Telephone createSimpleTelephone() {
        Telephone telephone = new Telephone();
        telephone.setPrice(BigDecimal.valueOf(300));
        return telephone;
    }

    private Television createSimpleTelevision() {
        Television television = new Television();
        television.setPrice(BigDecimal.valueOf(1500));
        return television;
    }

    private List<Invoice> createSimpleInvoiceList() {
        List<Invoice> invoices = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            invoices.add(createSimpleInvoice());
        }

        invoices.get(0).setCustomer(new Customer("email", 18));
        invoices.get(0).setType(InvoiceType.WHOLESALE);
        invoices.get(1).setCustomer(new Customer("email", 25));
        invoices.get(1).setType(InvoiceType.RETAIL);
        invoices.get(2).setCustomer(new Customer("email", 15));
        invoices.get(2).setType(InvoiceType.WHOLESALE);
        return invoices;
    }


    @Test
    public void getAmountSoldTelephones() {
        when(shopService.getInvoices()).thenReturn(createSimpleInvoiceList());
        int expected = 6;
        int actual = target.getAmountSoldTelephones();
        assertEquals(expected, actual);
    }

    @Test
    public void getAmountSoldTelevisions() {
        when(shopService.getInvoices()).thenReturn(createSimpleInvoiceList());
        int expected = 3;
        int actual = target.getAmountSoldTelevisions();
        assertEquals(expected, actual);
    }

    @Test
    public void getInvoiceBySmallestTotalPrice() {
        List<Invoice> invoices = createInvoiceListContainsOnlyTelevisions();
        invoices.addAll(createInvoiceListContainsOnlyTelephones());
        invoices.addAll(createSimpleInvoiceList());
        when(shopService.getInvoices()).thenReturn(invoices);
        Invoice actual = target.getInvoiceBySmallestTotalPrice();
        Invoice expected = createSimpleInvoice();
        assertEquals(expected.getProducts(), actual.getProducts());
        assertEquals(expected.getCustomer(), actual.getCustomer());
    }

    @Test
    public void calculateTotalPriceForInvoice() {
        when(shopService.getInvoices()).thenReturn(createSimpleInvoiceList());
        List<Invoice> invoices = shopService.getInvoices();
        BigDecimal actual = target.calculateTotalPriceForInvoice(invoices.get(0).getProducts());
        BigDecimal expected = new BigDecimal("2100");
        assertEquals(expected, actual);
    }

    @Test
    public void getTotalPriceForAllPurchases() {
        when(shopService.getInvoices()).thenReturn(createSimpleInvoiceList());
        BigDecimal actual = target.getTotalPriceForAllPurchases();
        BigDecimal expected = new BigDecimal("6300");
        assertEquals(expected, actual);
    }

    @Test
    public void getAmountInvoicesWhereTypeEqualsRetail() {
        when(shopService.getInvoices()).thenReturn(createSimpleInvoiceList());
        long actual = target.getAmountInvoicesWhereTypeEqualsRetail();
        assertEquals(1L, actual);
    }

    @Test
    public void getInvoicesContainsOneProductType_onlyTelephones() {
        when(shopService.getInvoices()).thenReturn(createInvoiceListContainsOnlyTelephones());
        List<Invoice> actual = target.getInvoicesContainsOneProductType();
        assertEquals(createInvoiceListContainsOnlyTelephones(), actual);
    }

    @Test
    public void getInvoicesContainsOneProductType_onlyTelevisions() {
        when(shopService.getInvoices()).thenReturn(createInvoiceListContainsOnlyTelevisions());
        List<Invoice> actual = target.getInvoicesContainsOneProductType();
        assertEquals(createInvoiceListContainsOnlyTelevisions(), actual);
    }

    @Test
    public void getInvoicesContainsOneProductType_noSuchInvoices() {
        when(shopService.getInvoices()).thenReturn(createSimpleInvoiceList());
        List<Invoice> actual = target.getInvoicesContainsOneProductType();
        assertEquals(Collections.emptyList(), actual);
    }

    private List<Invoice> createInvoiceListContainsOnlyTelephones() {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoiceHasOnlyTelephones = new Invoice();
        Map<Product, Integer> products = getProductsContainsOnlyTelephones();
        invoiceHasOnlyTelephones.setProducts(products);
        invoices.add(invoiceHasOnlyTelephones);
        invoices.add(invoiceHasOnlyTelephones);
        return invoices;
    }

    private Map<Product, Integer> getProductsContainsOnlyTelephones() {
        Map<Product, Integer> products = new HashMap<>();
        Telephone expensiveTelephone = new Telephone();
        expensiveTelephone.setPrice(BigDecimal.valueOf(1000));
        products.put(expensiveTelephone, 3);
        return products;
    }

    private List<Invoice> createInvoiceListContainsOnlyTelevisions() {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoiceHasOnlyTelevisions = new Invoice();
        Map<Product, Integer> products = getProductsContainsOnlyTelevisions();
        invoiceHasOnlyTelevisions.setProducts(products);
        invoices.add(invoiceHasOnlyTelevisions);
        return invoices;
    }

    private Map<Product, Integer> getProductsContainsOnlyTelevisions() {
        Map<Product, Integer> products = new HashMap<>();
        Television television = new Television();
        television.setPrice(BigDecimal.valueOf(2000));
        products.put(television, 4);
        return products;
    }

    @Test
    public void getFirstInvoices_checkSize() {
        when(shopService.getInvoices()).thenReturn(createSimpleInvoiceList());
        List<Invoice> firstInvoices = target.getFirstInvoices(1);
        assertNotNull(firstInvoices);
        assertEquals(1, firstInvoices.size());
    }

    @Test
    public void getFirstInvoices() {
        when(shopService.getInvoices()).thenReturn(createSimpleInvoiceList());
        List<Invoice> firstInvoices = target.getFirstInvoices(1);
        Invoice expected = createSimpleInvoice();
        Invoice actual = firstInvoices.get(0);
        assertEquals(expected.getProducts(), actual.getProducts());
        assertEquals(expected.getCustomer(), actual.getCustomer());
    }

    private Invoice createSimpleInvoice() {
        Invoice invoice = new Invoice();
        HashMap<Product, Integer> products = createSimpleProducts();
        invoice.setProducts(products);
        invoice.setCustomer(new Customer("email", 18));
        return invoice;
    }

    private HashMap<Product, Integer> createSimpleProducts() {
        Telephone simpleTelephone = createSimpleTelephone();
        Television simpleTelevision = createSimpleTelevision();
        HashMap<Product, Integer> products = new HashMap<>();
        products.put(simpleTelephone, 2);
        products.put(simpleTelevision, 1);
        return products;
    }

    @Test
    public void getInvoicesWhereCustomerIsUnderage() {
        when(shopService.getInvoices()).thenReturn(createSimpleInvoiceList());
        List<Invoice> actual = target.getInvoicesWhereCustomerIsUnderage();
        assertTrue(actual.stream().allMatch(invoice -> invoice.getCustomer().getAge() < 18));
    }

}
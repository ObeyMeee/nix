package ua.com.andromeda.module2;

import ua.com.andromeda.module2.entity.Invoice;
import ua.com.andromeda.module2.service.ShopService;
import ua.com.andromeda.module2.service.ShopStatistics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final BufferedReader CONSOLE_READER = new BufferedReader(new InputStreamReader(System.in));
    private static final ShopService SHOP_SERVICE = ShopService.getInstance();

    public static void main(String[] args) throws IOException {
        System.out.print("Input limit of purchase ==> ");
        BigDecimal limit = new BigDecimal(CONSOLE_READER.readLine());
        for (int i = 0; i < 15; i++) {
            Invoice invoice = SHOP_SERVICE.createRandomInvoice(limit);
            SHOP_SERVICE.saveInvoice(invoice);
        }
        final ShopStatistics shopStatistics = new ShopStatistics(SHOP_SERVICE);
        System.out.println("Sold telephones ==> " + shopStatistics.getAmountSoldTelephones());
        System.out.println("Sold televisions ==> " + shopStatistics.getAmountSoldTelevisions());

        Invoice smallestInvoice = shopStatistics.getInvoiceBySmallestTotalPrice();
        System.out.println("Total sum of the smallest invoice ==> " +
                shopStatistics.calculateTotalPriceForInvoice(smallestInvoice.getProducts()));

        System.out.println("Customer of the smallest invoice ==> " + smallestInvoice.getCustomer());
        System.out.println("Sum of all of the invoices ==> " + shopStatistics.getTotalPriceForAllPurchases());
        System.out.println("Amount of invoices where type=RETAIL ==> " +
                shopStatistics.getAmountInvoicesWhereTypeEqualsRetail());

        System.out.println("\nInvoices which has only one type of product:\n");
        shopStatistics.getInvoicesContainsOneProductType().forEach(System.out::println);

        System.out.println("First 3 invoices:\n");
        shopStatistics.getFirstInvoices(3).forEach(System.out::println);

        System.out.println("\n\nInvoices where customer is underage:\n");
        shopStatistics.getInvoicesWhereCustomerIsUnderage().forEach(System.out::println);

        System.out.println("\n\nSorted all invoices:\n");
        SHOP_SERVICE.sort();
        List<Invoice> sortedInvoices = SHOP_SERVICE.getInvoices();
        sortedInvoices.forEach(System.out::println);
    }
}

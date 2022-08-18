package ua.com.andromeda.module2;

import ua.com.andromeda.module2.entity.Invoice;
import ua.com.andromeda.module2.service.ShopService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final BufferedReader CONSOLE_READER = new BufferedReader(new InputStreamReader(System.in));
    private static final ShopService SHOP_SERVICE = ShopService.getInstance();

    public static void main(String[] args) throws IOException, IllegalAccessException {
        System.out.print("Input limit of purchase ==> ");
        BigDecimal limit = new BigDecimal(CONSOLE_READER.readLine());
        for (int i = 0; i < 15; i++) {
            Invoice invoice = SHOP_SERVICE.createRandomInvoice(limit);
            SHOP_SERVICE.saveInvoice(invoice);
        }

        System.out.println("Sold telephones ==> " + SHOP_SERVICE.getAmountSoldTelephones());
        System.out.println("Sold televisions ==> " + SHOP_SERVICE.getAmountSoldTelevisions());

        Invoice smallestInvoice = SHOP_SERVICE.getInvoiceBySmallestTotalPrice();
        System.out.println("Total sum of the smallest invoice ==> " +
                SHOP_SERVICE.calculateTotalPriceForInvoice(smallestInvoice.getProducts()));

        System.out.println("Customer of the smallest invoice ==> " + smallestInvoice.getCustomer());
        System.out.println("Sum of all of the invoices ==> " + SHOP_SERVICE.getTotalPriceForAllPurchases());
        System.out.println("Amount of invoices where type=RETAIL ==> " +
                SHOP_SERVICE.getAmountInvoicesWhereTypeEqualsRetail());

        System.out.println("\nInvoices which has only one type of product:\n");
        SHOP_SERVICE.getInvoicesContainsOneProductType().forEach(System.out::println);

        System.out.println("First 3 invoices:\n");
        SHOP_SERVICE.getFirstInvoices(3).forEach(System.out::println);

        System.out.println("\n\nInvoices where customer is underage:\n");
        SHOP_SERVICE.getInvoicesWhereCustomerIsUnderage().forEach(System.out::println);

        System.out.println("\n\nSorted all invoices:\n");
        SHOP_SERVICE.sort();
        List<Invoice> sortedInvoices = SHOP_SERVICE.getInvoices();
        sortedInvoices.forEach(System.out::println);
    }
}

package ua.com.andromeda;

import ua.com.andromeda.config.ApplicationContext;
import ua.com.andromeda.model.Invoice;
import ua.com.andromeda.service.InvoiceService;
import ua.com.andromeda.utils.UserInputUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;


public class MainInvoices {
    public static void main(String[] args) {
        InvoiceService invoiceService = ApplicationContext.getInstance().get(InvoiceService.class);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int count = random.nextInt(1, 10);
            invoiceService.createAndSaveInvoice(count);
        }

        System.out.println("Total count of invoices ==> " + invoiceService.getInvoicesCount());

        int totalPrice = UserInputUtils.getInt("Input total price ==> ");
        System.out.println("\nInvoices where total price greater than " + totalPrice + "\n");
        List<Invoice> invoices = invoiceService.getInvoicesWhereTotalPriceGreaterThan(BigDecimal.valueOf(totalPrice));
        invoices.forEach(System.out::println);

        String id = UserInputUtils.getString("\nInput id to update time ==> ");
        invoiceService.updateTime(id);

        System.out.println("Grouped invoices by id");
        invoiceService.groupByTotalPrice().forEach((k, v) -> System.out.printf("%s ==> %s%n", k, v));
    }
}

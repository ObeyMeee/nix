package ua.com.andromeda.module2.service;

import ua.com.andromeda.module2.entity.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ShopStatistics {
    private final List<Invoice> invoices;

    public ShopStatistics(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public int getAmountSoldTelephones() {
        return invoices.stream()
                .flatMap(invoice -> invoice.getProducts().entrySet().stream())
                .filter(productIntegerEntry -> productIntegerEntry.getKey() instanceof Telephone)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public int getAmountSoldTelevisions() {
        return invoices.stream()
                .flatMap(invoice -> invoice.getProducts().entrySet().stream())
                .filter(productIntegerEntry -> productIntegerEntry.getKey() instanceof Television)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public Invoice getInvoiceBySmallestTotalPrice() {
        return invoices.stream()
                .min((invoice1, invoice2) -> {
                    BigDecimal totalPriceForInvoice1 = calculateTotalPriceForInvoice(invoice1.getProducts());
                    BigDecimal totalPriceForInvoice2 = calculateTotalPriceForInvoice(invoice2.getProducts());
                    return totalPriceForInvoice1.compareTo(totalPriceForInvoice2);
                })
                .orElseThrow();
    }

    public BigDecimal calculateTotalPriceForInvoice(Map<Product, Integer> products) {
        return products.entrySet()
                .stream()
                .map(this::calculateTotalPricePerProduct)
                .reduce(BigDecimal::add)
                .orElseThrow();
    }

    private BigDecimal calculateTotalPricePerProduct(Map.Entry<Product, Integer> productIntegerEntry) {
        BigDecimal pricePerUnit = productIntegerEntry.getKey().getPrice();
        int quantity = productIntegerEntry.getValue();
        return pricePerUnit.multiply(new BigDecimal(quantity));
    }

    public BigDecimal getTotalPriceForAllPurchases() {
        return invoices.stream()
                .flatMap(invoice -> invoice.getProducts().entrySet().stream())
                .map(this::calculateTotalPricePerProduct)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public long getAmountInvoicesWhereTypeEqualsRetail() {
        return invoices.stream()
                .filter(invoice -> invoice.getType().equals(InvoiceType.RETAIL))
                .count();
    }

    public List<Invoice> getInvoicesContainsOneProductType() {
        return invoices.stream()
                .filter(invoice -> {
                    Set<Product> products = invoice.getProducts().keySet();
                    boolean isAllProductsTelephones = products.stream().allMatch(Telephone.class::isInstance);
                    boolean isAllProductsTelevisions = products.stream().allMatch(Television.class::isInstance);
                    return isAllProductsTelephones || isAllProductsTelevisions;
                }).toList();
    }

    public List<Invoice> getFirstInvoices(int count) {
        return invoices.subList(0, count);
    }

    public List<Invoice> getInvoicesWhereCustomerIsUnderage() {
        return invoices.stream()
                .filter(invoice -> invoice.getCustomer().getAge() < 18)
                .peek(invoice -> invoice.setType(InvoiceType.LOW_AGE))
                .toList();
    }
}

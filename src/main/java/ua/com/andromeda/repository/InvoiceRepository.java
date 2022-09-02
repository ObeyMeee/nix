package ua.com.andromeda.repository;

import ua.com.andromeda.model.Invoice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface InvoiceRepository {
    boolean save(Invoice invoice);

    List<Invoice> getInvoicesWhereTotalPriceGreaterThan(BigDecimal totalPrice);

    int getInvoicesCount();

    void updateTime(String id);

    Map<String, BigDecimal> groupByTotalPrice();
}

package ua.com.andromeda.module2.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class Invoice {
    private Map<Product, Integer> products;
    private Customer customer;
    private InvoiceType type;
}

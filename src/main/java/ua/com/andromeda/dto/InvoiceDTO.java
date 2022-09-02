package ua.com.andromeda.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceDTO {
    private String invoiceId;
    private BigDecimal totalPrice;
}

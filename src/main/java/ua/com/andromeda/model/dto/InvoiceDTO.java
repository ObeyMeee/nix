package ua.com.andromeda.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceDTO {
    private String invoiceId;
    private BigDecimal totalPrice;
}

package io.github.zaragozamartin91;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Receipt {
    private static final int TOTAL_PRICE_LENGTH = 12;
    private static final int UNIT_PRICE_LENGTH = 12;
    private static final int DESCRIPTION_LENGTH = 20;
    private static final int QUANTITY_LENGTH = 6;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    private final LocalDateTime now;
    private final List<PurchaseItem> purchaseItems;
    private Discount discount;

    FormatPrice formatPrice = new FormatPrice();

    public List<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public Receipt(
            LocalDateTime now,
            List<PurchaseItem> purchaseItems,
            Discount discount) {
        this.now = now;
        this.purchaseItems = purchaseItems;
        this.discount = discount;
    }

    public List<String> getLines() {
        ArrayList<String> lines = new ArrayList<>();

        // Fecha 25/01/2024
        String formattedDate = DATE_FORMATTER.format(now);
        lines.add(" ");
        lines.add("Ferreteria");
        lines.add(" ");
        lines.add("Fecha: " + formattedDate);
        lines.add(" ");

        List<String> lineItems = writeLineItems();
        int maxLineItemLenght = lineItems.stream().mapToInt(String::length).max().orElse(0);
        String headers = writeHeaders();
        int divisionLineLength = Math.max(maxLineItemLenght, headers.length());

        // Cant.    Descripcion     SubTot.     Total
        lines.add(headers);
        // -------------------------------------
        lines.add(drawLine(divisionLineLength)); 
        lines.addAll(lineItems);
        lines.add(drawLine(divisionLineLength));
        // -------------------------------------

        lines.add(" ");

        BigDecimal fullPrice = PurchaseItem.getFullPrice(purchaseItems);
        String fullPriceText = formatPrice.apply(fullPrice);
        String fullPricePrompt = StringBlock.padRight("Subtotal:", 12).getValue();
        lines.add(fullPricePrompt + StringBlock.padLeft(fullPriceText, 16).getValue());


        BigDecimal discountAmount = discount.calculateAmount(fullPrice);
        String discountAmountText = formatPrice.apply(discountAmount);
        String discountPrompt = StringBlock.padRight("Descuento:", 12).getValue();
        lines.add(discountPrompt + StringBlock.padLeft(discountAmountText, 16).getValue());


        BigDecimal totalPrice = discount.apply(fullPrice);
        String totalPriceText = formatPrice.apply(totalPrice);
        String totalPricePrompt = StringBlock.padRight("Total:", 12).getValue();
        lines.add(totalPricePrompt + StringBlock.padLeft(totalPriceText, 16).getValue());


        lines.add(" ");

        lines.add("DOCUMENTO NO VALIDO COMO FACTURA");

        return lines;
    }

    private List<String> writeLineItems() {
        return purchaseItems.stream().map(purchaseItem -> {
            String quantity = StringBlock.padRight(purchaseItem.normalizeQuantity(), QUANTITY_LENGTH).getValue();
            String description = StringBlock.padRight(purchaseItem.getDescription(), DESCRIPTION_LENGTH).getValue();
            String unitPrice = StringBlock.padRight(purchaseItem.normalizeUnitPrice(), UNIT_PRICE_LENGTH).getValue();
            String totalPrice = StringBlock.padRight(purchaseItem.normalizeTotalPrice(), TOTAL_PRICE_LENGTH).getValue();
            return quantity + description + unitPrice + totalPrice;
        }).collect(Collectors.toList());
    }

    private String drawLine(int length) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i<length ; i++) sb.append("-");
        return sb.toString();
    }

    private String writeHeaders() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringBlock.padRight("Cant.", QUANTITY_LENGTH).getValue());
        sb.append(StringBlock.padRight("Descripcion", DESCRIPTION_LENGTH).getValue());
        sb.append(StringBlock.padRight("SubTot.", UNIT_PRICE_LENGTH).getValue());
        sb.append(StringBlock.padRight("Total", TOTAL_PRICE_LENGTH).getValue());
        return sb.toString();
    }
}

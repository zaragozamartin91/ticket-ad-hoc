package io.github.zaragozamartin91;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Receipt {
    private static final String EMPTY_LINE = " ";
    private static final int MAX_LINE_LENGTH = 32;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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
        lines.add(EMPTY_LINE);
        lines.add("Ferreteria");
        lines.add(EMPTY_LINE);
        lines.add("Fecha: " + formattedDate);
        lines.add(EMPTY_LINE);

        // Cant.    Descripcion     SubTot.     Total
        lines.addAll(writeHeaders());
        // -------------------------------------
        lines.add(drawLine(MAX_LINE_LENGTH)); 
        lines.addAll(writeLineItems());
        lines.add(drawLine(MAX_LINE_LENGTH));
        // -------------------------------------

        lines.add(EMPTY_LINE);

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
        return purchaseItems.stream().flatMap(pi -> {
            String description = truncate(pi.getDescription());
            String priceFormula = String.format("%s x %s = %s", pi.normalizeQuantity(), pi.normalizeUnitPrice(), pi.normalizeTotalPrice());
            return Stream.of(description, priceFormula, EMPTY_LINE);
        }).collect(Collectors.toList());
    }

    private String drawLine(int length) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i<length ; i++) sb.append("-");
        return sb.toString();
    }

    private List<String> writeHeaders() {
        return Stream.of(
            "Descripcion",
            "Cant x Precio = Total"
        ).collect(Collectors.toList());
    }

    private String truncate(String s) {
        if (s.isEmpty()) return s;
        return s.substring(0, Math.min(s.length(), MAX_LINE_LENGTH));
    }
}

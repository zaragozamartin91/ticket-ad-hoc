package io.github.zaragozamartin91;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Optional;

public class PurchaseItem {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private static final String PRICE_REGEX = "\\d+\\.?\\d{1,2}";

    private int quantity;
    private String description;
    private BigDecimal price;

    public PurchaseItem(int quantity, String description, BigDecimal price) {
        this.quantity = quantity;
        this.description = description;
        this.price = price;
    }

    public static PurchaseItem fromRawInput(String quantity, String description, String price) {
        return new PurchaseItem(
                parseQuantity(quantity),
                parseDescription(description),
                parsePrice(price));
    }

    static int parseQuantity(String quantity) {
        try {
            return Optional.ofNullable(quantity)
                    .map(Integer::valueOf)
                    .filter(v -> v >= 0)
                    .orElseThrow(() -> new IllegalArgumentException("Quantity must be equal or higher than zero"));
        } catch (Exception e) {
            throw new InvalidQuantityException("Quantity " + quantity + " is invalid", e, quantity);
        }
    }

    static String parseDescription(String description) {
        try {
            return Optional.ofNullable(description)
                    .filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .orElseThrow(() -> new IllegalArgumentException("Description must be a non-empty string"));
        } catch (Exception e) {
            throw new InvalidDescriptionException("Description " + description + " is invalid", e, description);
        }
    }

    static BigDecimal parsePrice(String price) {
        try {
            return Optional.ofNullable(price)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> s.matches(PRICE_REGEX))
                    .map(s -> new BigDecimal(s))
                    .orElseThrow(() -> new IllegalArgumentException("Price must follow format '" + PRICE_REGEX + "'"));
        } catch (IllegalArgumentException e) {
            throw new InvalidPriceException("Price " + price + " is invalid", e, price);
        }
    }

    public String normalizeQuantity() {
        return String.valueOf(quantity);
    }

    public String normalizeUnitPrice() {
        return DECIMAL_FORMAT.format(this.price);
    }

    public BigDecimal getTotalPrice() {
        return this.price.multiply(BigDecimal.valueOf(quantity));
    }

    public String normalizeTotalPrice() {
        return DECIMAL_FORMAT.format(getTotalPrice());
    }

    public String getDescription() {
        return description.trim();
    }
}

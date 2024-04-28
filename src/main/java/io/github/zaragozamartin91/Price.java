package io.github.zaragozamartin91;

import java.math.BigDecimal;
import java.util.Optional;

public class Price {
    private static final String PRICE_REGEX = "\\d+\\.?\\d{1,2}";

    private BigDecimal price;

    public Price(BigDecimal price) {
        this.price = price;
    }

    public static Price fromRawInput(String price) {
        return new Price(parsePrice(price));
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

    public BigDecimal getPrice() {
        return price;
    }
}

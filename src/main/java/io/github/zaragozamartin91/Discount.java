package io.github.zaragozamartin91;

import java.math.BigDecimal;
import java.util.Optional;

public class Discount {
    private static final String DISCOUNT_REGEX = "\\d+(\\.\\d{1,2})?";
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100L);
    private static final BigDecimal ONE = BigDecimal.valueOf(1L);

    private BigDecimal percentage;

    public Discount(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public static Discount fromRawInput(String discount) {
        return new Discount(parseDiscount(discount));
    }

    static BigDecimal parseDiscount(String discount) {
        try {
            return Optional.ofNullable(discount)
                    .filter(s -> !s.isEmpty())
                    .filter(s -> s.matches(DISCOUNT_REGEX))
                    .map(s -> new BigDecimal(s))
                    .filter(v -> ONE_HUNDRED.compareTo(v) >= 0) // discount cannot be higher than 100%
                    .orElseThrow(() -> new IllegalArgumentException("Discount must follow format '" + DISCOUNT_REGEX + "'"));
        } catch (IllegalArgumentException e) {
            throw new InvalidDiscountException("Discount " + discount + " is invalid", e, discount);
        }
    }

    static Discount zero() {
        return new Discount(BigDecimal.ZERO);
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public BigDecimal calculateAmount(BigDecimal otherAmount) {
        return this.toDecimal().multiply(otherAmount);
    }

    public BigDecimal apply(BigDecimal otherAmount) {
        return otherAmount.subtract(calculateAmount(otherAmount));
    }
    
    public BigDecimal toDecimal() {
        return this.percentage.divide(ONE_HUNDRED);
    }
}

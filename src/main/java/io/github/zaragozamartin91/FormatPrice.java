package io.github.zaragozamartin91;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.function.Function;

public class FormatPrice implements Function<BigDecimal, String> {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Override
    public String apply(BigDecimal t) {
        return DECIMAL_FORMAT.format(t);
    }
}

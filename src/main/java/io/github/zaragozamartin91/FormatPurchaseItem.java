package io.github.zaragozamartin91;

import java.util.function.Function;

public class FormatPurchaseItem implements Function<PurchaseItem, String> {

    @Override
    public String apply(PurchaseItem purchaseItem) {
        String quantity = purchaseItem.normalizeQuantity();
        String description = purchaseItem.getDescription();
        String unitPrice = purchaseItem.normalizeUnitPrice();
        String totalPrice = purchaseItem.normalizeTotalPrice();

        return new StringBuilder()
                .append(quantity)
                .append(" ")
                .append(description)
                .append("\t")
                .append(unitPrice)
                .append(" ")
                .append(totalPrice)
                .toString();
    }

}

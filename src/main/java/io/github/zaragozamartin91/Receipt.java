package io.github.zaragozamartin91;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Receipt {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final LocalDateTime now;
    private final List<PurchaseItem> purchaseItems;

    public List<PurchaseItem> getPurchaseItems() {
        return purchaseItems;
    }

    public Receipt(LocalDateTime now, List<PurchaseItem> purchaseItems) {
        this.now = now;
        this.purchaseItems = purchaseItems;
    }

    public String format() {
        StringBuilder sb = new StringBuilder();

        sb.append(DATE_FORMATTER.format(now));
        sb.append("\n");
        
        

        return sb.toString();
    }
}

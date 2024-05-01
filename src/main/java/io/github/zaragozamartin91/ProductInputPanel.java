package io.github.zaragozamartin91;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class ProductInputPanel extends JPanel {
    List<SingleProductInput> singleInputs;

    public ProductInputPanel() {
        singleInputs = new ArrayList<>();
        this.setBorder(new EmptySymmetricBorder(StandardDimension.BORDER));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(Box.createVerticalGlue());
    }

    public ProductInputPanel addSingleProductInput(SingleProductInput singleProductInput) {
        this.add(singleProductInput);
        singleInputs.add(singleProductInput);
        return this;
    }

    public List<PurchaseItem> getPurchaseItems() {
        return singleInputs.stream()
            .map(SingleProductInput::toPurchaseItem)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    public BigDecimal getFullPrice() {
        return PurchaseItem.getFullPrice(getPurchaseItems());
    }

    public void clear() {
        singleInputs.stream().forEach(SingleProductInput::clear);
    }
}

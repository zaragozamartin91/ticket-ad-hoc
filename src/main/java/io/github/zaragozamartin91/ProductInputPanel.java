package io.github.zaragozamartin91;

import java.util.ArrayList;
import java.util.List;
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

    public List<SingleProductInput> getInputs() {
        return new ArrayList<>(singleInputs);
    }

    public List<String> normalizePurchaseItems() {
        return singleInputs.stream()
            .map(SingleProductInput::normalizePurchaseItem)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
    }
}

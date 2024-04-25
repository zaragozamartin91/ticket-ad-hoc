package io.github.zaragozamartin91;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SingleProductInput extends JPanel {
    private static final int PADDING = 10;
    private static final int BORDER_SIZE = 5;

    private final JTextField quantityField;
    private final JTextField descriptionField;
    private final JTextField priceField;

    SingleProductInput() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(new EmptySymmetricBorder(BORDER_SIZE));

        this.add(new JLabel("Cantidad"));
        this.add(Box.createRigidArea(new Dimension(PADDING, 0)));
        quantityField = new JTextField();
        quantityField.setColumns(2);
        this.add(quantityField);

        this.add(Box.createRigidArea(new Dimension(PADDING, 0)));

        this.add(new JLabel("Detalle"));
        this.add(Box.createRigidArea(new Dimension(PADDING, 0)));
        descriptionField = new JTextField();
        descriptionField.setColumns(16);
        this.add(descriptionField);

        this.add(Box.createRigidArea(new Dimension(PADDING, 0)));

        this.add(new JLabel("Precio U."));
        this.add(Box.createRigidArea(new Dimension(PADDING, 0)));
        priceField = new JTextField();
        priceField.setColumns(4);
        this.add(priceField);
    }

    SingleProductInput addTo(Container container) {
        container.add(this);
        return this;
    }

    public String getQuantity() {
        return quantityField.getText().trim();
    }

    public String getDescription() {
        return descriptionField.getText().trim();
    }

    public String getPrice() {
        return priceField.getText().trim();
    }

    public PurchaseItem toPurchaseItem() {
        return PurchaseItem.fromRawInput(getQuantity(), getDescription(), getPrice());
    }
}

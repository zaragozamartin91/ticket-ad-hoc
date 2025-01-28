package io.github.zaragozamartin91;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SingleProductInput extends JPanel {
    private static final int PADDING = 10;
    private static final int BORDER_SIZE = 5;

    private final JTextField quantityField;
    private final JTextField descriptionField;
    private final JTextField priceField;

    SingleProductInput(Consumer<PurchaseItem> pConsumer) {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(new EmptySymmetricBorder(BORDER_SIZE));

        // https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield
        DocumentListener quantityOrPriceChangeListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { onChange(); }
            public void removeUpdate(DocumentEvent e) { onChange(); }
            public void insertUpdate(DocumentEvent e) { onChange(); }
            
            public void onChange() {
                try {
                    SingleProductInput.this.toPurchaseItem().ifPresent(pConsumer);
                } catch (Exception e) {
                    System.err.println("On quantity or price change :: failed to parse purchase item");
                }
            }
        };

        this.add(new JLabel("Cantidad"));
        this.add(Box.createRigidArea(new Dimension(PADDING, 0)));
        quantityField = new JTextField();
        quantityField.setColumns(2);
        quantityField.getDocument().addDocumentListener(quantityOrPriceChangeListener);
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
        priceField.getDocument().addDocumentListener(quantityOrPriceChangeListener);
        this.add(priceField);
    }

    void resetCursor() {
        quantityField.grabFocus();
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

    public boolean isEmpty() {
        return getQuantity().isEmpty() 
            && getDescription().isEmpty() 
            && getPrice().isEmpty();
    }

    public Optional<PurchaseItem> toPurchaseItem() {
        if (this.isEmpty()) { return Optional.empty(); }
        
        PurchaseItem pi = PurchaseItem.fromRawInput(getQuantity(), getDescription(), getPrice());
        return Optional.ofNullable(pi);
    }

    public void clear() {
        quantityField.setText("");
        descriptionField.setText("");
        priceField.setText("");
    }
}

package io.github.zaragozamartin91;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SingleInput extends JPanel {
    private static final int PADDING = 10;
    private static final int BORDER_SIZE = 5;
    private final JTextField descriptionField;
    private final JTextField priceField;

    SingleInput() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(new EmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));

        this.add(new JLabel("Descripcion"));
        this.add(Box.createRigidArea(new Dimension(PADDING, 0)));
        descriptionField = new JTextField();
        this.add(descriptionField);

        this.add(Box.createRigidArea(new Dimension(PADDING, 0)));

        this.add(new JLabel("Precio [$]"));
        this.add(Box.createRigidArea(new Dimension(PADDING, 0)));
        priceField = new JTextField();
        this.add(priceField);
    }

    SingleInput addTo(Container container) {
        container.add(this);
        return this;
    }

    public String getDescription() {
        return descriptionField.getText().trim();
    }

    public String getPrice() {
        return priceField.getText().trim();
    }
}

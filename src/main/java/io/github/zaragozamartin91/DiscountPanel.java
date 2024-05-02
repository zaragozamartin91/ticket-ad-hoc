package io.github.zaragozamartin91;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DiscountPanel extends JPanel {
    private static final String DEFAULT_DISCOUNT_TEXT = "10.00";

    JCheckBox applyDiscountCheckBox;
    JTextField discountPercentageInput;

    public DiscountPanel(Consumer<Boolean> onCheckboxToggle) {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        applyDiscountCheckBox = new JCheckBox("Aplicar descuento [%] ");
        this.add(applyDiscountCheckBox);
        discountPercentageInput = new JTextField(DEFAULT_DISCOUNT_TEXT);
        this.add(discountPercentageInput);
        this.add(Box.createRigidArea(new Dimension(256, 0)));

        discountPercentageInput.setEnabled(false);

        applyDiscountCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Checkbox clicked");
                JCheckBox s = (JCheckBox) e.getSource();
                refreshDiscountInput(s);
                onCheckboxToggle.accept(s.isSelected());
            }
        });

        
        DocumentListener discountInputChangeListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { onChange(); }
            public void removeUpdate(DocumentEvent e) { onChange(); }
            public void insertUpdate(DocumentEvent e) { onChange(); }
            
            public void onChange() {
                try {
                    onCheckboxToggle.accept(true);
                } catch (Exception e) {
                    System.err.println("On discount change :: failed to call listener");
                }
            }
        };
        discountPercentageInput.getDocument().addDocumentListener(discountInputChangeListener);
    }

    public Discount getDiscount() {
        return applyDiscountCheckBox.isSelected()
            ? Discount.fromRawInput(discountPercentageInput.getText())
            : Discount.zero();
    }

    public void clear() {
        applyDiscountCheckBox.setSelected(false);
        discountPercentageInput.setText(DEFAULT_DISCOUNT_TEXT);
        refreshDiscountInput(applyDiscountCheckBox);
    }

    private void refreshDiscountInput(JCheckBox s) {
        discountPercentageInput.setEnabled(s.isSelected());
    }
}

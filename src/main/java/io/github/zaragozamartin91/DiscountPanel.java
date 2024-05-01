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

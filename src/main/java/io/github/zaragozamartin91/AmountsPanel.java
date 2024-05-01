package io.github.zaragozamartin91;

import java.awt.Component;
import java.awt.Dimension;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AmountsPanel extends JPanel {
    JLabel fullPriceLabel;
    JLabel discountLabel;
    JLabel netPriceLabel;

    BigDecimal fullPriceAmount;
    BigDecimal discountAmount;
    BigDecimal netPriceAmount;

    FormatPrice formatPrice = new FormatPrice();

    AmountsPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        fullPriceAmount = BigDecimal.ZERO;
        discountAmount = BigDecimal.ZERO;
        netPriceAmount = BigDecimal.ZERO;

        fullPriceLabel = new JLabel("Subtotal: " + format(fullPriceAmount));
        discountLabel = new JLabel("Descuento: " + format(discountAmount));
        netPriceLabel = new JLabel("Total: " + format(netPriceAmount));

        
        this.add(fullPriceLabel);
        this.add(rigidArea());
        this.add(new JLabel("|"));
        this.add(rigidArea());
        this.add(discountLabel);
        this.add(rigidArea());
        this.add(new JLabel("|"));
        this.add(rigidArea());
        this.add(netPriceLabel);
        this.add(Box.createHorizontalGlue());
    }

    private Component rigidArea() {
        return Box.createRigidArea(new Dimension(12, 0));
    }

    String format(BigDecimal bd) {
        return StringBlock.padLeft(formatPrice.apply(bd), 24).getValue();
    }

    public void setFullPriceAmount(BigDecimal fullPriceAmount) {
        this.fullPriceAmount = fullPriceAmount;
        fullPriceLabel.setText("Subtotal: " + format(fullPriceAmount));
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
        discountLabel.setText("Descuento: " + format(discountAmount));
    }

    public void setNetPriceAmount(BigDecimal netPriceAmount) {
        this.netPriceAmount = netPriceAmount;
        netPriceLabel.setText("Total: " + format(netPriceAmount));
    }

    public void clear() {
        setFullPriceAmount(BigDecimal.ZERO);
        setDiscountAmount(BigDecimal.ZERO);
        setNetPriceAmount(BigDecimal.ZERO);
    }
}

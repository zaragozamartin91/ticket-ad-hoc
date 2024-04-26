package io.github.zaragozamartin91;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;

public class PrinterPanel extends JScrollPane {
    private final JList<String> printServicesList;

    private PrinterPanel(JList<String> printServicesList) {
        super(printServicesList);
        this.printServicesList = printServicesList;

        this.setBorder(new EmptySymmetricBorder(StandardDimension.BORDER));
        this.setMinimumSize(new Dimension(0, 80));
    }

    public static PrinterPanel fromPrinterNames(String[] printServicesNames) {
        return new PrinterPanel(new JList<>(printServicesNames));
    }

    public String getSelectedValue() {
        return printServicesList.getSelectedValue();
    }
}

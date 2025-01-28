package io.github.zaragozamartin91;

import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class PrinterPanel extends JScrollPane {
    private final JList<String> printServicesList;

    private PrinterPanel(JList<String> listModel) {
        super(listModel);
        this.printServicesList = listModel;

        this.setBorder(new EmptySymmetricBorder(StandardDimension.BORDER));
        this.setMinimumSize(new Dimension(0, 80));


        ListModel<String> model = listModel.getModel();
        for (int i = 0 ; i < model.getSize() ; i++) {
            String printerName = model.getElementAt(i);
            if(printerName.matches("POS\\-.*")) printServicesList.setSelectedIndex(i);
        }
    }

    public static PrinterPanel fromPrinterNames(String[] printServicesNames) {
        JList<String> jlist = new JList<>(printServicesNames);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return new PrinterPanel(jlist);
    }

    public String getSelectedValue() {
        return printServicesList.getSelectedValue();
    }

    public void setSelectedIndex(int index) {
        printServicesList.setSelectedIndex(index);
    }
}

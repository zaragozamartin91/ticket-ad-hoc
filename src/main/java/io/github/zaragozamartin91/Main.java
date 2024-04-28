package io.github.zaragozamartin91;

import com.github.anastaciocintra.output.PrinterOutputStream;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Main {
    public static void main(String[] args) throws IOException {
        // consolePoc(args);
        // swingPoc(args);
        boxLayoutPoc2();
    }

    public static void boxLayoutPoc2() {
        JFrame frame = new JFrame("Imprimir ticket ad-hoc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(640, 480));

        String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();
        PrinterPanel printerPanel = PrinterPanel.fromPrinterNames(printServicesNames);

        // Lay out the label and scroll pane from top to bottom.
        ProductInputPanel productInputPanel = new ProductInputPanel();

        SingleProductInput singleProductInput = new SingleProductInput();
        productInputPanel.addSingleProductInput(singleProductInput);

        
        DiscountPanel discountPanel = new DiscountPanel();

        // Lay out the buttons from left to right.
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.add(Box.createHorizontalGlue());
        JButton printButton = new JButton("Imprimir");
        printButton.addActionListener(e -> {
            String selectedPrinter = printerPanel.getSelectedValue();
            if (selectedPrinter == null) {
                JOptionPane.showMessageDialog(frame, "Seleccione una impresora!","Impresora vacia", JOptionPane.ERROR_MESSAGE);
                System.out.println("No printer selected.");
                return;
            }

            System.out.println("Selected Printer: " + selectedPrinter);

            List<String> lines = new ArrayList<>();
            try {
                List<PurchaseItem> purchaseItems = productInputPanel.getPurchaseItems();
                Discount discount = discountPanel.getDiscount();
                LocalDateTime now = LocalDateTime.now();

                if (purchaseItems.isEmpty()) {
                    lines = new ArrayList<>();
                } else {
                    Receipt receipt = new Receipt(now, purchaseItems, discount);
                    lines = receipt.getLines();
                }
            } catch (InvalidQuantityException e1) {
                JOptionPane.showMessageDialog(
                    frame, 
                    "Cantidad "+ e1.getQuantityInput() +" invalida!\nLa cantidad debe ser un numero mayor a 0",
                    "Cantidad invalida", 
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (InvalidDescriptionException e2) {
                JOptionPane.showMessageDialog(
                    frame,
                     "Detalle "+ e2.getDescription() +" invalido!\nEl detalle de un producto no puede ser vacio.",
                    "Detalle invalido",
                     JOptionPane.ERROR_MESSAGE
                );
            } catch (InvalidPriceException e3) {
                JOptionPane.showMessageDialog(
                    frame, 
                    "Precio "+ e3.getPrice() +" invalido!\nPrecio debe ser un valor numerico con 0, 1 o 2 posiciones decimales.\nEjemplos: 3 ; 3.5 ; 4.02 ; 5.0 ; 5.00",
                    "Precio invalido", 
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (InvalidDiscountException e3) {
                JOptionPane.showMessageDialog(
                    frame, 
                    "Descuento "+ e3.getPrice() +" invalido!\nDescuento debe ser un valor porrcentual con 0, 1 o 2 posiciones decimales.\nEjemplos: 10 ; 12.3 ; 13.05",
                    "Descuento invalido", 
                    JOptionPane.ERROR_MESSAGE
                );
            } 

            if (lines.isEmpty()) {
                System.out.println("Nothing to print...");
                return;
            }

            System.out.println("Printing...");
            lines.forEach(System.out::println);
            new PrintLines().run(selectedPrinter, lines);
        });
        buttonPanel.add(printButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton addFieldButton = new JButton("Agregar producto");
        addFieldButton.addActionListener((ActionEvent e) -> {
            SingleProductInput singleProductInput_2 = new SingleProductInput();
            productInputPanel.addSingleProductInput(singleProductInput_2);

            System.out.println("Adding field...");
            frame.pack(); // pack everything tight
            frame.setVisible(true); // refresh the view
        });
        buttonPanel.add(addFieldButton);
        
        // Put everything together, using the content pane's BorderLayout.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(buttonPanel);

        JLabel printerLabel = new JLabel("Impresoras");
        JPanel printerLabelPanel = new JPanel();
        printerLabelPanel.setLayout(new BoxLayout(printerLabelPanel, BoxLayout.LINE_AXIS));
        printerLabelPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        printerLabelPanel.add(printerLabel);
        printerLabelPanel.add(Box.createHorizontalGlue());
        mainPanel.add(printerLabelPanel);

        
        mainPanel.add(printerPanel);
        mainPanel.add(discountPanel);
        mainPanel.add(productInputPanel);        

        Container contentPane = frame;
        contentPane.add(mainPanel, BorderLayout.NORTH);
        // contentPane.add(buttonPane, BorderLayout.PAGE_START);
        // contentPane.add(listScroller, BorderLayout.CENTER);
        // contentPane.add(inputAreaPanel, BorderLayout.PAGE_END);

        // center the frame
        frame.setLocationRelativeTo(null);
        // Make the frame visible
        frame.setVisible(true);
        frame.pack();
    }

    private static void consolePoc(String[] args) throws IOException, UnsupportedEncodingException {
        new PrinterConsolePoc().accept(args);
    }

    private static void swingPoc(String[] args) throws IOException, UnsupportedEncodingException {
        new PrinterSwingPoc().run();
    }

    public static void boxLayoutPoc() {
        JFrame frame = new JFrame("Impresoras disponibles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();

        // Create a JList and set its model to display the printServicesNames array
        JList<String> printServicesList = new JList<>(printServicesNames);

        JScrollPane listScroller = new JScrollPane(printServicesList);
        listScroller.setPreferredSize(new Dimension(250, 80));

        // Lay out the label and scroll pane from top to bottom.
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Sample label");
        label.setHorizontalAlignment(SwingConstants.LEFT);

        listPane.add(label);
        listPane.add(Box.createRigidArea(new Dimension(0, 5)));
        listPane.add(listScroller);
        listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Lay out the buttons from left to right.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(new JButton("Cancel"));
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(new JButton("Accept"));

        // Put everything together, using the content pane's BorderLayout.
        Container contentPane = frame;
        contentPane.add(listPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);

        // center the frame
        frame.setLocationRelativeTo(null);
        // Make the frame visible
        frame.setVisible(true);

    }

}

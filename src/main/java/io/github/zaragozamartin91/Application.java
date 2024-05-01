package io.github.zaragozamartin91;

import com.github.anastaciocintra.output.PrinterOutputStream;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Application implements Runnable {
    JFrame frame;
    PrinterPanel printerPanel;
    ProductInputPanel productInputPanel;
    DiscountPanel discountPanel;
    JPanel buttonPanel;
    AmountsPanel amountsPanel;
    JPanel mainPanel;

    String[] printServicesNames;

    Application() {
        printServicesNames = PrinterOutputStream.getListPrintServicesNames();
    }

    public void run() {
        frame = new JFrame("Imprimir ticket ad-hoc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(640, 480));

        printerPanel = PrinterPanel.fromPrinterNames(printServicesNames);

        // Lay out the label and scroll pane from top to bottom.
        productInputPanel = new ProductInputPanel();
        discountPanel = new DiscountPanel(b -> refreshAmountLabels());

        // Lay out the buttons from left to right.
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.add(Box.createHorizontalGlue());
        JButton printButton = new JButton("Imprimir");
        printButton.addActionListener(e -> {
            printButton.setEnabled(false);
            CompletableFuture.runAsync(() -> {
                try {Thread.sleep(1000L);} catch (Exception __e) {}
                printButton.setEnabled(true);
            });

            String selectedPrinter = printerPanel.getSelectedValue();
            if (selectedPrinter == null) {
                showErrorMessage("Seleccione una impresora!","Impresora vacia");
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
                showErrorMessage("Cantidad "+ e1.getQuantityInput() +" invalida!\nLa cantidad debe ser un numero mayor a 0", "Cantidad invalida");
            } catch (InvalidDescriptionException e2) {
                showErrorMessage("Detalle "+ e2.getDescription() +" invalido!\nEl detalle de un producto no puede ser vacio.", "Detalle invalido");
            } catch (InvalidPriceException e3) {
                showErrorMessage(
                    "Precio "+ e3.getPrice() +" invalido!\nPrecio debe ser un valor numerico con 0, 1 o 2 posiciones decimales.\nEjemplos: 3 ; 3.5 ; 4.02 ; 5.0 ; 5.00", 
                    "Detalle invalido"
                );
            } catch (InvalidDiscountException e3) {
                showErrorMessage(
                    "Descuento "+ e3.getPrice() +" invalido!\nDescuento debe ser un valor porrcentual con 0, 1 o 2 posiciones decimales.\nEjemplos: 10 ; 12.3 ; 13.05", 
                    "Descuento invalido"
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
        addFieldButton.addActionListener((ActionEvent e) -> addSingleProductInput());
        buttonPanel.add(addFieldButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton clearButton = new JButton("LIMPIAR");
        clearButton.addActionListener(e -> {
            productInputPanel.clear();
            discountPanel.clear();
            amountsPanel.clear();
        });
        buttonPanel.add(clearButton);

        // Printer label
        JLabel printerLabel = new JLabel("Impresoras");
        JPanel printerLabelPanel = new JPanel();
        printerLabelPanel.setLayout(new BoxLayout(printerLabelPanel, BoxLayout.LINE_AXIS));
        printerLabelPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        printerLabelPanel.add(printerLabel);
        printerLabelPanel.add(Box.createHorizontalGlue());

        // amounts panel
        amountsPanel = new AmountsPanel();
        
        // Put everything together, using the content pane's BorderLayout.
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        mainPanel.add(buttonPanel);

        mainPanel.add(printerLabelPanel); 
        mainPanel.add(printerPanel);
        mainPanel.add(discountPanel);
        mainPanel.add(amountsPanel);
        addSingleProductInput();
        mainPanel.add(productInputPanel);        

        Container contentPane = frame;
        contentPane.add(mainPanel, BorderLayout.NORTH);

        // center the frame
        frame.setLocationRelativeTo(null);
        // Make the frame visible
        frame.setVisible(true);
        frame.pack();
    }

    private void addSingleProductInput() {
        SingleProductInput singleProductInput = new SingleProductInput(_purchaseItem -> {
            refreshAmountLabels();
        });
        productInputPanel.addSingleProductInput(singleProductInput);

        System.out.println("Adding field...");
        frame.pack(); // pack everything tight
        frame.setVisible(true); // refresh the view
    }

    private void refreshAmountLabels() {
        try {
            BigDecimal fullPrice = productInputPanel.getFullPrice();
            Discount discount = discountPanel.getDiscount();
            BigDecimal discountAmount = discount.calculateAmount(fullPrice);
            BigDecimal totalPrice = discount.apply(fullPrice);
            amountsPanel.setFullPriceAmount(fullPrice);
            amountsPanel.setDiscountAmount(discountAmount);
            amountsPanel.setNetPriceAmount(totalPrice);
        } catch (Exception e) {
            System.err.println("Failed to update amount labels");
        }
    }

    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }
}

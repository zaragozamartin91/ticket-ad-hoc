package io.github.zaragozamartin91;

import com.github.anastaciocintra.output.PrinterOutputStream;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Main {
    public static void main(String[] args) throws IOException {
        // consolePoc(args);
        // swingPoc(args);
        boxLayoutPoc2();
    }

    private static List<SingleProductInput> singleInputs = new ArrayList<SingleProductInput>();

    public static void boxLayoutPoc2() {
        JFrame frame = new JFrame("Imprimir ticket ad-hoc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(640, 480));

        String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();

        // Create a JList and set its model to display the printServicesNames array
        JList<String> printServicesList = new JList<>(printServicesNames);
        JScrollPane printerScrollPanel = new JScrollPane(printServicesList);
        printerScrollPanel.setBorder(new EmptySymmetricBorder(StandardDimension.BORDER));
        printerScrollPanel.setMinimumSize(new Dimension(0, 80));

        // Lay out the label and scroll pane from top to bottom.
        JPanel inputAreaPanel = new JPanel();
        inputAreaPanel.setBorder(new EmptySymmetricBorder(StandardDimension.BORDER));
        inputAreaPanel.setLayout(new BoxLayout(inputAreaPanel, BoxLayout.PAGE_AXIS));
        inputAreaPanel.add(Box.createVerticalGlue());

        SingleProductInput singleInputPanel = new SingleProductInput();
        singleInputPanel.addTo(inputAreaPanel);
        singleInputs.add(singleInputPanel);

        // Lay out the buttons from left to right.
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        JButton printButton = new JButton("Imprimir");
        printButton.addActionListener(e -> {
            String selectedPrinter = printServicesList.getSelectedValue();
            if (selectedPrinter != null) {
                System.out.println("Selected Printer: " + selectedPrinter);
                List<String> lines = singleInputs.stream()
                    .filter(si -> !si.getDescription().isEmpty() || !si.getPrice().isEmpty())
                    .map(si -> si.getDescription() + " ... " + si.getPrice()).collect(Collectors.toList());
                System.out.println("Printing " + lines);
                if(!lines.isEmpty()) {new PrintLines().run(selectedPrinter, lines);}
            } else {
                JOptionPane.showMessageDialog(frame, "Seleccione una impresora!","Impresora vacia", JOptionPane.ERROR_MESSAGE);
                System.out.println("No printer selected.");
            }
            
        });
        buttonPane.add(printButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton addFieldButton = new JButton("Agregar producto");
        addFieldButton.addActionListener((ActionEvent e) -> {
            SingleProductInput singleInputPanel_2 = new SingleProductInput();
            singleInputPanel_2.addTo(inputAreaPanel);
            singleInputs.add(singleInputPanel_2);

            System.out.println("Adding field...");
            frame.pack(); // pack everything tight
            frame.setVisible(true); // refresh the view
        });
        buttonPane.add(addFieldButton);

        // Put everything together, using the content pane's BorderLayout.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(buttonPane);

        JLabel printerLabel = new JLabel("Impresoras");
        JPanel printerLabelPanel = new JPanel();
        printerLabelPanel.setLayout(new BoxLayout(printerLabelPanel, BoxLayout.LINE_AXIS));
        printerLabelPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        printerLabelPanel.add(printerLabel);
        printerLabelPanel.add(Box.createHorizontalGlue());
        mainPanel.add(printerLabelPanel);

        
        mainPanel.add(printerScrollPanel);
        mainPanel.add(inputAreaPanel);        

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

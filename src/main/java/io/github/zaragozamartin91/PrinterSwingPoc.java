package io.github.zaragozamartin91;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.github.anastaciocintra.output.PrinterOutputStream;

public class PrinterSwingPoc implements Runnable {
    public void run() {
        String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();
        
        System.out.println("Lista de impresoras:");
        for (String printServiceName : printServicesNames) {
            System.out.println(printServiceName);
        }

        // Create a new JFrame
        JFrame frame = new JFrame("Impresoras disponibles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);


        JTextField text = new JTextField();
        frame.add(text, BorderLayout.NORTH);

        // Create a JList and set its model to display the printServicesNames array
        JList<String> printServicesList = new JList<>(printServicesNames);
        frame.add(new JScrollPane(printServicesList), BorderLayout.CENTER);
    

        // Create a button to capture the selected printer
        JButton captureButton = new JButton("Seleccionar impresora");
        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Capture the selected printer's name
                String selectedPrinter = printServicesList.getSelectedValue();
                if (selectedPrinter != null) {
                    System.out.println("Selected Printer: " + selectedPrinter);
                    new PrintDemoMessage().accept(selectedPrinter);
                } else {
                    System.out.println("No printer selected.");
                }
            }
        });
        frame.add(captureButton, BorderLayout.SOUTH);


        // center the frame
        frame.setLocationRelativeTo(null);
        // Make the frame visible
        frame.setVisible(true);
    }

}

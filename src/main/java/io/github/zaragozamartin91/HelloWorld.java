package io.github.zaragozamartin91;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;

import javax.print.PrintService;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HelloWorld {
    public static void main(String[] args) throws IOException {
        // consolePoc(args);
        swingPoc(args);
    }

    private static void consolePoc(String[] args) throws IOException, UnsupportedEncodingException {
        if (args.length != 1) {
            System.out.println("Uso: java -jar thermal-printer-1-jar-with-dependencies.jar \"Nombre de impresora\"");
            System.out.println(
                    "Ejemplo: java -jar thermal-printer-1-jar-with-dependencies.jar \"HPBCE92FA96048(HP Laser 103 107 108)\"");
            System.out.println("Lista de impresoras:");

            String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();
            for (String printServiceName : printServicesNames) {
                System.out.println(printServiceName);
            }

            System.exit(0);
        }

        String printServiceName = args[0];
        printDemo(printServiceName);
    }


    private static void swingPoc(String[] args) throws IOException, UnsupportedEncodingException {
        String[] printServicesNames = PrinterOutputStream.getListPrintServicesNames();

        System.out.println("Lista de impresoras:");
        for (String printServiceName : printServicesNames) {
            System.out.println(printServiceName);
        }

        // Create a new JFrame
        JFrame frame = new JFrame("Impresoras disponibles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

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
                    printDemo(selectedPrinter);
                } else {
                    System.out.println("No printer selected.");
                }
            }
        });
        frame.add(captureButton, BorderLayout.SOUTH);


        frame.setLocationRelativeTo(null);
        // Make the frame visible
        frame.setVisible(true);
    }

    private static void printDemo(String printServiceName) {
        PrintService printService = PrinterOutputStream.getPrintServiceByName(printServiceName);
        try (PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService)) {
            EscPos escpos = new EscPos(printerOutputStream);
            escpos.writeLF("Te quiero mucho papa");
            escpos.feed(5).cut(EscPos.CutMode.FULL);
            escpos.close();
        } catch (IllegalArgumentException | IOException e) {
            System.err.println("Ocurrio un error al intentar imprimir");
            e.printStackTrace();
        }
    }
}

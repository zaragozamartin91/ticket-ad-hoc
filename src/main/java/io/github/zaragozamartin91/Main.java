package io.github.zaragozamartin91;

import com.github.anastaciocintra.output.PrinterOutputStream;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class Main {
    public static void main(String[] args) throws IOException {
        new Application().run();
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

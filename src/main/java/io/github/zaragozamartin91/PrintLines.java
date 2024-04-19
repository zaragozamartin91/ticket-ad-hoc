package io.github.zaragozamartin91;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import javax.print.PrintService;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;

public class PrintLines {
    public void run(String printServiceName, List<String> lines) {
        PrintService printService = PrinterOutputStream.getPrintServiceByName(printServiceName);

        try (PrinterOutputStream printerOutputStream = new PrinterOutputStream(printService)) {
            EscPos escpos = new EscPos(printerOutputStream);

            for (String line : lines) {
                escpos.writeLF(line);
            }

            escpos.writeLF("");
            escpos.writeLF("Ticket no valido como factura");

            escpos.feed(5).cut(EscPos.CutMode.FULL);
            escpos.close();
        } catch (IllegalArgumentException | IOException e) {
            System.err.println("Ocurrio un error al intentar imprimir");
            e.printStackTrace();
        }
    }
}

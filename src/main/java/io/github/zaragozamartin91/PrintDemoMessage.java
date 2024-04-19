package io.github.zaragozamartin91;

import java.io.IOException;
import java.util.function.Consumer;

import javax.print.PrintService;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.output.PrinterOutputStream;

public class PrintDemoMessage implements Consumer<String> {
    public void accept(String printServiceName) {
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

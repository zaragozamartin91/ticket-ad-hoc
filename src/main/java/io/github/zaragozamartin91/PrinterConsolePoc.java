package io.github.zaragozamartin91;

import java.util.function.Consumer;

import com.github.anastaciocintra.output.PrinterOutputStream;

public class PrinterConsolePoc implements Consumer<String[]> {
        public void accept(String[] args) {
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
        new PrintDemoMessage().accept(printServiceName);
    }
}

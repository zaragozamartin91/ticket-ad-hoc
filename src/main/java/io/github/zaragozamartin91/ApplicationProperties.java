package io.github.zaragozamartin91;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

class ApplicationProperties {
    Properties props;

    static ApplicationProperties load() {
        Properties props = new Properties();
        Path path = Path.of("ticket-ad-hoc-config.properties");
        try {
            props.load(Files.newBufferedReader(path));
            return new ApplicationProperties(props);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load application properties from " + path, e);
        }
    }

    static ApplicationProperties empty() {
        return new ApplicationProperties(new Properties());
    }

    ApplicationProperties(Properties props) {
        this.props = props;
    }

    public int defaultPrinterIndex() {
        String strIdx = props.getOrDefault("default_printer", "0").toString();
        try {
            int idx = Integer.parseInt(strIdx);
            return Math.max(idx - 1, 0);
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse default printer index from " + strIdx + "; defaulting to 0");
            return 0;
        }
    }
}

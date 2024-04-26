package io.github.zaragozamartin91;

public class StringBlock {
    private final String value;

    public String getValue() {
        return value;
    }

    private StringBlock(String value) {
        this.value = value;
    }

    public static StringBlock padLeft(String value, int maxSize) {
        String padding = buildPadding(value, maxSize);
        return new StringBlock( padding.concat(value) );
    }

    public static StringBlock padRight(String value, int maxSize) {
        String padding = buildPadding(value, maxSize);
        return new StringBlock( value.concat(padding) );
    }

    private static String buildPadding(String value, int maxSize) {
        int paddingSize = Math.max(0, maxSize - value.length());
        StringBuilder paddingBuilder = new StringBuilder();
        for (int i = 0 ; i < paddingSize ; i ++ , paddingBuilder.append(" ")) ;
        return paddingBuilder.toString();
    }
}

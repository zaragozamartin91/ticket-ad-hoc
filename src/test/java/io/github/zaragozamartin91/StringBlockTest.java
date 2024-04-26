package io.github.zaragozamartin91;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class StringBlockTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void padLeftShouldPrependWhitespace() {
        String value = "xxxx";
        int maxSize = 8;
        StringBlock stringBlock = StringBlock.padLeft(value, maxSize);
        assertEquals("    xxxx", stringBlock.getValue());
    }

    @Test
    public void padRightShouldAppendWhitespace() {
        String value = "xxxx";
        int maxSize = 8;
        StringBlock stringBlock = StringBlock.padRight(value, maxSize);
        assertEquals("xxxx    ", stringBlock.getValue());
    }

    @Test
    public void padLeftShouldPrependNothingIfStringIsLongerThanSize() {
        String value = "xxxxxxxxxxxx";
        int maxSize = value.length() - 1;
        StringBlock stringBlock = StringBlock.padLeft(value, maxSize);
        assertEquals(value, stringBlock.getValue());
    }
}

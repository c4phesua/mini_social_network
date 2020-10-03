package utils;

import java.nio.charset.StandardCharsets;

public class StringLib {
    public static String toUnicode(String src) {
        return new String(src.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}

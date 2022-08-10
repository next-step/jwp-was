package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static String decodeUrlEncoding(String data) {
        return URLDecoder.decode(data, StandardCharsets.UTF_8);
    }
}

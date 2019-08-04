package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class StringUtils {
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String unescape(String string) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(string)) return "";
        return URLDecoder.decode(string, DEFAULT_CHARSET);
    }

    public static String escape(String string) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(string)) return "";
        return URLEncoder.encode(string, DEFAULT_CHARSET);
    }
}

package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class StringUtils {
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String unescape(String string) {
        if (StringUtils.isBlank(string)) return "";
        try {
            return URLDecoder.decode(string, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }

    public static String escape(String string) {
        if (StringUtils.isBlank(string)) return "";
        try {
            return URLEncoder.encode(string, DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }
}

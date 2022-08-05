package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    private HttpUtils() {
        throw new AssertionError();
    }

    public static Map<String, Object> parseParameters(String string, Pattern keyValuePattern) {
        final Map<String, Object> result = new HashMap<>();
        final String decode = decode(string);
        final Matcher matcher = keyValuePattern.matcher(decode);

        while (matcher.find()) {
            result.put(matcher.group(1), matcher.group(2));
        }
        return result;
    }

    private static String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            return "";
        }
    }
}

package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    private static final String ITEM_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int CORRECT_LENGTH = 2;
    public static final String VALIDATION_MESSAGE = "형식에 맞지 않습니다.";

    private HttpUtils() {
        throw new AssertionError();
    }

    public static String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            return "";
        }
    }

    public static Map<String, String> parseToMap(String string) {
        final Map<String, String> result = new HashMap<>();
        final String[] items = string.split(ITEM_DELIMITER);
        for (String item : items) {
            addItem(result, item);
        }
        return result;
    }

    private static void addItem(Map<String, String> result, String item) {
        final String[] keyValue = item.split(KEY_VALUE_DELIMITER);
        validateKeyValue(keyValue);
        result.put(keyValue[0], decode(keyValue[1]));
    }

    private static void validateKeyValue(String[] items) {
        if (items.length != CORRECT_LENGTH) {
            throw new IllegalArgumentException(VALIDATION_MESSAGE);
        }
    }
}

package utils.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.logging.log4j.util.Strings;

public class HttpQueryStringParser {
    private static final String DELIMITER_FIELD = "&";
    private static final String DELIMITER_KEY_VALUE = "=";

    private static final int IDX_KEY = 0;
    private static final int IDX_VALUE = 1;

    public static Map<String,String> parse(String queryString) {
        if (Strings.isEmpty(queryString)) {
            return Map.of();
        }

        String[] queryStringFields = queryString.split(DELIMITER_FIELD);

        Map<String,String> result = new HashMap<>();

        for (String queryStringField : queryStringFields) {
            String[] keyValue = queryStringField.split(DELIMITER_KEY_VALUE);
            String key = keyValue[IDX_KEY];
            String value = keyValue[IDX_VALUE];

            result.put(key, value);
        }
        return result;
    }
}

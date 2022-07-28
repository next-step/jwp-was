package webserver.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpParams {

    private static final int VALID_LENGTH = 2;
    private static final String KEY_VALUE_SPLIT_REGEX = "=";
    private static final String PARAM_SPLIT_REGEX = "&";

    private final Map<String, String> params;

    public HttpParams() {
        this.params = new HashMap<>();
    }

    public HttpParams(String querystring) {
        this.params = toParams(querystring);
    }

    private Map<String, String> toParams(String querystring) {
        return Arrays.stream(querystring.split(PARAM_SPLIT_REGEX))
                .map(this::parseKeyValue)
                .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
    }

    private String[] parseKeyValue(String q) {
        String[] keyValue = q.split(KEY_VALUE_SPLIT_REGEX);
        if (isValidLength(keyValue)) {
            throw new IllegalArgumentException("invalid key=value: " + q);
        }
        return keyValue;
    }

    private static boolean isValidLength(String[] keyValue) {
        return keyValue.length != VALID_LENGTH;
    }
}

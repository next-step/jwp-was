package webserver.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import webserver.exception.InvalidRequestParamsException;

public class RequestParams {
    public static final String PARAM_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";

    private final Map<String, String> params;

    RequestParams() {
        this.params = new HashMap<>();
    }

    RequestParams(String queryString) {
        this.params = create(queryString);
    }

    private Map<String, String> create(String queryString) {
        return Arrays.stream(queryString.split(PARAM_DELIMITER))
                .map(param -> param.split(KEY_VALUE_DELIMITER))
                .map(keyValue -> {
                    checkKeyValueLength(keyValue);
                    return keyValue;
                })
                .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1]));
    }

    private void checkKeyValueLength(String[] requestParam) {
        if (requestParam.length != 2) {
            throw new InvalidRequestParamsException("유효하지 않은 Query String 입니다. key=value 형태여야 합니다.");
        }

        if (requestParam[0].isEmpty() || requestParam[1].isEmpty()) {
            throw new InvalidRequestParamsException("유효하지 않은 Query String 입니다. key=value 형태여야 합니다.");
        }
    }

    public Map<String, String> getParams() {
        return params;
    }
}

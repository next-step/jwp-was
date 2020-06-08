package http.request;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RequestBody {
    private static final String QUERY_STRING_REGEX = "&";
    private static final String KEY_VALUE_REGEX = "=";

    private Map<String, String> bodyMap;

    private RequestBody(Map<String, String> bodyMap) {
        this.bodyMap = bodyMap;
    }

    public static RequestBody parse(String value) {
        Map<String, String> bodyMap = new HashMap<>();

        String[] bodyValues = value.split(QUERY_STRING_REGEX);

        if (bodyValues.length < 2) {
            return new RequestBody(bodyMap);
        }

        for (int i = 0; i < bodyValues.length; i++) {
            String[] values = bodyValues[i].split(KEY_VALUE_REGEX);
            bodyMap.put(values[0], values[1]);
        }

        return new RequestBody(bodyMap);
    }
}

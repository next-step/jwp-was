package webserver.http.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Parameters {
    public static final String QUERY_STRING_DELIMITER = "&";
    public static final String KEY_VALUE_DELIMITER = "=";
    private final Map<String, String> parametersByKey = new HashMap<>();

    private Parameters() {
    }

    public static Parameters from(String queryString) {
        Parameters parameters = new Parameters();

        if (Objects.isNull(queryString) || queryString.isEmpty()) {
            return parameters;
        }

        Arrays.stream(queryString.split(QUERY_STRING_DELIMITER))
                .map(qs -> qs.split(KEY_VALUE_DELIMITER))
                .forEach(parameters::put);

        return parameters;
    }

    private void put(String[] keyValue) {
        parametersByKey.put(keyValue[0], keyValue[1]);
    }

    public String get(String key) {
        return parametersByKey.get(key);
    }
}

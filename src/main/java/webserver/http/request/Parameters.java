package webserver.http.request;

import java.util.*;

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

    public static Parameters from(Parameters... parametersList) {
        Parameters parameters = new Parameters();

        Arrays.stream(parametersList)
                .map(params -> params.parametersByKey.entrySet())
                .flatMap(Collection::stream)
                .forEach(entry -> parameters.parametersByKey.put(entry.getKey(), entry.getValue()));

        return parameters;
    }

    private void put(String[] keyValue) {
        parametersByKey.put(keyValue[0], keyValue[1]);
    }

    public String get(String key) {
        return parametersByKey.get(key);
    }
}

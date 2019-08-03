package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class Parameter {
    private static final Parameter EMPTY_PARAMETER = new Parameter(Collections.emptyMap());

    private final static int QUERY_KEY_INDEX = 0;
    private final static int QUERY_VALUE_INDEX = 1;

    private final static String PARAMETER_SEPARATOR = "&";
    private final static String KEY_VALUE_SEPARATOR = "=";

    private Map<String, String> parameter;

    private Parameter(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    public static Parameter parse(String queryString) {
        Map<String, String> query = parseParameter(queryString);

        return new Parameter(query);
    }

    private static Map<String, String> parseParameter(String queryString) {
        return Arrays.stream(queryString.split(PARAMETER_SEPARATOR))
            .map(array -> array.split(KEY_VALUE_SEPARATOR))
            .collect(Collectors.toMap(keyValue -> keyValue[QUERY_KEY_INDEX], keyValue -> keyValue[QUERY_VALUE_INDEX]));
    }

    public Map<String, String> getParameters() {
        return parameter;
    }

    public static Parameter empty() {
        return EMPTY_PARAMETER;
    }

    public String get(String key) {
        return parameter.get(key);
    }
}

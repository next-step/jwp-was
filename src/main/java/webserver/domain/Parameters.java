package webserver.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Parameters {
    public static final String QUERYSTRING_PARAM_DELIMITER = "&";
    public static final String QUERYSTRING_KEY_VALUE_DELIMITER = "=";
    public static final int QUERY_VALUE_POINT = 1;
    public static final String DEFAULT_QUERY_VALUE = "";

    private final Map<String, String> store;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Parameters(@JsonProperty("parameters") Map<String, String> store) {
        this.store = store;
    }

    public static Parameters from(String parameters) {
        return new Parameters(getParameterMap(parameters));
    }

    private static Map<String, String> getParameterMap(String parameters) {

        return Arrays.stream(parameters.split(QUERYSTRING_PARAM_DELIMITER))
                .map(str -> str.split(QUERYSTRING_KEY_VALUE_DELIMITER))
                .collect(Collectors.toMap(Parameters::queryKey, Parameters::queryValue));
    }

    private static String queryKey(String[] pair) {
        return pair[0];
    }

    private static String queryValue(String[] pair) {
        if (pair.length < 2) {
            return DEFAULT_QUERY_VALUE;
        }
        return pair[QUERY_VALUE_POINT];
    }

    public static Parameters emptyInstance() {
        return new Parameters(Collections.emptyMap());
    }

    public static Parameters newInstance() {
        return new Parameters(new HashMap<>());
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(store);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parameters that = (Parameters) o;
        return Objects.equals(store, that.store);
    }

    @Override
    public int hashCode() {
        return Objects.hash(store);
    }

    public String get(String key) {
        return store.get(key);
    }

    public void addParameters(String line) {
        store.putAll(getParameterMap(line));
    }

    public void addParameters(String key, String value) {
        store.put(key, value);
    }

    @Override
    public String toString() {
        return store.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue() + "\r\n")
                .collect(Collectors.joining());
    }
}

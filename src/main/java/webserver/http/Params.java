package webserver.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Params {

    private static final String PARAM_DELIMITER = "&";

    private Map<String, String> params;

    private Params() {
        this.params = new HashMap<>();
    }

    private Params(Map<String, String> params) {
        this.params = params;
    }

    public static Params from(String queryString) {
        return parseParams(queryString, PARAM_DELIMITER);
    }

    public static Params from(Map<String, String> params) {
        return new Params(params);
    }

    public static Params of(String queryString, String delimiter) {
        return parseParams(queryString, delimiter);
    }

    public Map<String, String> getParams() {
        return Collections.unmodifiableMap(params);
    }

    public String getParam(String key) {
        return params.get(key);
    }

    private static Params parseParams(String values, String delimiter) {
        if (values.isEmpty()) {
            return new Params();
        }
        String[] tokens = values.split(delimiter);
        return new Params(Arrays.stream(tokens)
                .map(KeyValue::of)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Params)) {
            return false;
        }
        Params params1 = (Params) o;
        return Objects.equals(getParams(), params1.getParams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParams());
    }
}

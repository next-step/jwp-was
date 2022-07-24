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
        return parseParams(queryString);
    }

    public static Params from(Map<String, String> params) {
        return new Params(params);
    }

    public Map<String, String> getParams() {
        return Collections.unmodifiableMap(params);
    }

    private static Params parseParams(String values) {
        if (values.isEmpty()) {
            return new Params();
        }
        String[] tokens = values.split(PARAM_DELIMITER);
        return new Params(Arrays.stream(tokens)
                .map(Param::of)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Param::getKey, Param::getValue)));
    }
}

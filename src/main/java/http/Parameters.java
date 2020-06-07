package http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Parameters {

    private final static Parameters empty = new Parameters(Collections.EMPTY_MAP);

    private final Map<String, String> parameterMap;

    public Parameters(Map<String, String> parameterMap) {
        this.parameterMap = new HashMap<>(parameterMap);
    }

    public static Parameters empty() {
        return empty;
    }

    public void addAll(Parameters parameters) {
        this.parameterMap.putAll(parameters.parameterMap);
    }

    public String get(String name) {
        return parameterMap.get(name);
    }
}

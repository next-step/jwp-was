package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
    private static final Parameter EMPTY_PARAMETER = new Parameter(new HashMap<>());

    private final static int QUERY_KEY_INDEX = 0;
    private final static int QUERY_VALUE_INDEX = 1;

    private final static String PARAMETER_SEPARATOR = "&";
    private final static String KEY_VALUE_SEPARATOR = "=";

    private Map<String, String> parameter;

    private Parameter(Map<String, String> parameter) {
        this.parameter = parameter;
    }

    public static Parameter parse(String querySting) {
        Map<String, String> query = new HashMap<>();
        setKeyAndValue(querySting, query);

        return new Parameter(query);
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


    private static void setKeyAndValue(String querySting, Map<String, String> query) {
        for(String param : querySting.split(PARAMETER_SEPARATOR)) {
            String[] keyValue = param.split(KEY_VALUE_SEPARATOR);
            query.put(keyValue[QUERY_KEY_INDEX], keyValue[QUERY_VALUE_INDEX]);
        }
    }

}

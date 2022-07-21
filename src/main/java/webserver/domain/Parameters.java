package webserver.domain;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;

public class Parameters {
    private static final String PARAMETERS_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final int ONLY_KEY_LENGTH = 1;

    private final Map<String, String> values = new HashMap<>();

    public Parameters(String queryString) {
        String[] params = queryString.split(PARAMETERS_DELIMITER);
        stream(params).forEach(this::setParameters);
    }

    private void setParameters(String param) {
        String[] keyValue = param.split(KEY_VALUE_DELIMITER);

        if (keyValue.length == ONLY_KEY_LENGTH) {
            values.put(keyValue[0], "");
            return;
        }
        values.put(keyValue[0], keyValue[1]);
    }

    public Map<String, String> getParameters() {
        return values;
    }

    public String getParameter(String key) {
        return values.get(key);
    }
}

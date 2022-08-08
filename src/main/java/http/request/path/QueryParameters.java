package http.request.path;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class QueryParameters {

    private static final String DATA_DELIMITER = "&";
    private static final String VALUE_DELIMITER = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> pairs;

    private QueryParameters(Map<String, String> pairs) {
        this.pairs = Collections.unmodifiableMap(pairs);
    }

    public static QueryParameters from(String queryString) {
        String[] params = queryString.split(DATA_DELIMITER);
        Map<String, String> dataPairs = new HashMap<>();

        for (String param : params) {
            String[] data = param.split(VALUE_DELIMITER);
            dataPairs.put(data[KEY_INDEX], data[VALUE_INDEX]);
        }
        return new QueryParameters(dataPairs);
    }

    public String get(String key) {
        return pairs.get(key);
    }
}

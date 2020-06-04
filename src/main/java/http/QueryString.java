package http;

import java.util.HashMap;

public class QueryString {

    private static final String PARAMETER_DELIMITER = "&";
    private static final String VALUE_DELIMITER = "=";

    private HashMap<String, String> param = new HashMap<>();

    public QueryString(String value) {
        String[] values = value.split(PARAMETER_DELIMITER);
        for (String s : values) {
            String[] query = s.split(VALUE_DELIMITER);
            param.put(query[0], query[1]);
        }
    }

    public HashMap<String, String> getParam() {
        return param;
    }
}

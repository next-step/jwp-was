package request;

import java.util.HashMap;
import java.util.Map;

public class Path {

    private static final String QUERY_DELIMITER = "\\?";
    private static final String CONDITION_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    private static final int NAME_INDEX = 0;
    private static final int QUERY_INDEX = 1;

    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private Map<String, String> conditions = new HashMap<>();
    private final String name;

    public Path(String request) {
        String[] query = request.split(QUERY_DELIMITER);
        this.name = query[NAME_INDEX];

        if (query.length > 1) {
            this.conditions = parseConditions(query[QUERY_INDEX]);
        }
    }

    private Map<String, String> parseConditions(String query) {
        Map<String, String> conditions = new HashMap<>();
        String[] splitQuery = query.split(CONDITION_DELIMITER);

        for (String condition : splitQuery) {
            String[] keyValue = condition.split(KEY_VALUE_DELIMITER);
            conditions.put(keyValue[KEY_INDEX], keyValue[VALUE_INDEX]);
        }

        return conditions;
    }

    public String getValue(String key) {
        return conditions.get(key);
    }

    public String getName() {
        return name;
    }
}

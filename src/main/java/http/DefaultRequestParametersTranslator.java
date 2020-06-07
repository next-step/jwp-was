package http;

import java.util.HashMap;
import java.util.Map;

public class DefaultQueryStringsTranslator implements QueryStringsTranslator {

    private static final String SEPARATOR_QUERY_STRING = "&";
    private static final String SEPARATOR_KEY_VALUE = "=";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private final String queryString;

    public DefaultQueryStringsTranslator(final String queryString) {
        this.queryString = queryString;
    }

    @Override
    public Map<String, String> create() {
        Map<String, String> queryStrings = new HashMap<>();
        String[] queries = queryString.split(SEPARATOR_QUERY_STRING);
        for (final String query : queries) {
            String[] keyAndValues = query.split(SEPARATOR_KEY_VALUE);
            queryStrings.put(keyAndValues[KEY_INDEX], keyAndValues[VALUE_INDEX]);
        }
        return queryStrings;
    }
}

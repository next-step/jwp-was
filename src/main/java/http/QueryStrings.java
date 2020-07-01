package http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class QueryStrings {

    private static final int PREV = 0;
    private static final int NEXT = 1;

    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String ESCAPE_QUEST = "\\?";
    private static final String EQUALS = "=";
    private static final String COMMA = ",";

    private final Map<String, List<String>> queryStrings;

    public QueryStrings(String path) {
        this.queryStrings = new HashMap<>();
        if (path.contains(QUESTION_MARK)) {
            final String[] pathValues = path.split(ESCAPE_QUEST);
            String values = pathValues[NEXT];
            buildQueryStrings(values);
        }
    }

    private void buildQueryStrings(final String values) {
        if (values.contains(AMPERSAND)) {
            String[] tokens = values.split(AMPERSAND);
            Stream.of(tokens).forEach(this::putValue);
        }
        putValue(values);
    }

    private void putValue(final String values) {
        String[] tokens = values.split(EQUALS);
        queryStrings.put(tokens[PREV], Arrays.asList(tokens[NEXT].split(COMMA)));
    }

    @Override
    public String toString() {
        return "QueryStrings{" +
                "queryStrings=" + queryStrings +
                '}';
    }
}

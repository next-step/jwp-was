package webserver;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class HttpQueryStrings implements Iterable<HttpQueryString> {
    public static final String PRIMARY_QUERY_STRING_SYMBOL = "\\?";
    public static final String SECONDARY_QUERY_STRING_SYMBOL = "&";
    public static final int QUERY_STRING_SCHEMA_START_INDEX = 1;
    public static final int QUERY_STRING_SCHEMA_REQUIRED_SIZE = 2;

    private final List<HttpQueryString> httpQueryStrings;
    private final String fullQueryString;

    public HttpQueryStrings(String fullQueryString) {
        this.httpQueryStrings = parseQueryString(fullQueryString);
        this.fullQueryString = toFullQueryString(fullQueryString);
    }

    private List<HttpQueryString> parseQueryString(String fullQueryString) {
        if (isEmptyQueryString(fullQueryString)) {
            return Collections.emptyList();
        }

        String[] firstQueryStringSchemas = fullQueryString.split(PRIMARY_QUERY_STRING_SYMBOL);
        validateFullQueryString(fullQueryString, firstQueryStringSchemas);

        String[] fullQueryStringSchemas = firstQueryStringSchemas[QUERY_STRING_SCHEMA_START_INDEX].split(SECONDARY_QUERY_STRING_SYMBOL);

        return Arrays.stream(fullQueryStringSchemas)
                .map(HttpQueryString::new)
                .collect(Collectors.toList());
    }

    private String toFullQueryString(String fullQueryString) {
        if (isEmptyQueryString(fullQueryString)) {
            return "";
        }

        return fullQueryString;
    }

    private boolean isEmptyQueryString(String fullQueryString) {
        return fullQueryString.isEmpty() || PRIMARY_QUERY_STRING_SYMBOL.replace("\\", "").equals(fullQueryString);
    }

    private void validateFullQueryString(String fullQueryString, String[] firstQueryStringSchemas) {
        if (firstQueryStringSchemas.length != QUERY_STRING_SCHEMA_REQUIRED_SIZE) {
            throw new IllegalArgumentException(String.format("잘못된 queryString 입니다. queryString: [%s]", fullQueryString));
        }
    }

    public HttpQueryString get(int index) {
        if (index > httpQueryStrings.size() - 1) {
            return null;
        }

        return httpQueryStrings.get(index);
    }

    public String getFullQueryString() {
        return fullQueryString;
    }

    @Override
    public Iterator<HttpQueryString> iterator() {
        return httpQueryStrings.iterator();
    }
}

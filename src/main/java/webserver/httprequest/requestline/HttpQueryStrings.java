package webserver.httprequest.requestline;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static webserver.httprequest.requestline.HttpPath.PRIMARY_QUERY_STRING_SYMBOL;

public class HttpQueryStrings {
    public static final String PRIMARY_QUERY_STRING_SYMBOL_WITHOUT_ESCAPE = PRIMARY_QUERY_STRING_SYMBOL.replace("\\", "");
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

        String[] fullQueryStringSchemas = fullQueryString.split(SECONDARY_QUERY_STRING_SYMBOL);

        return Arrays.stream(fullQueryStringSchemas)
                .map(HttpQueryString::from)
                .filter(HttpQueryString::isNotEmpty)
                .collect(Collectors.toList());
    }

    private String toFullQueryString(String fullQueryString) {
        if (isEmptyQueryString(fullQueryString)) {
            return "";
        }

        return PRIMARY_QUERY_STRING_SYMBOL_WITHOUT_ESCAPE + fullQueryString;
    }

    private boolean isEmptyQueryString(String fullQueryString) {
        return fullQueryString == null || fullQueryString.isEmpty();
    }

    public HttpQueryString get(int index) {
        if (index > httpQueryStrings.size() - 1) {
            return HttpQueryString.EMPTY;
        }

        return httpQueryStrings.get(index);
    }

    public String getFullQueryString() {
        return fullQueryString;
    }
}

package webserver.http;

import java.util.*;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query {
    private static final Pattern QUERY_PARAM_PATTERN = Pattern.compile("([^&=]+)=?([^&]+)?");

    private final Map<String, String> queryParams;

    private Query(final Map<String, String> queryParams) {
        this.queryParams = new LinkedHashMap<>(queryParams);
    }

    static Query parse(final String queryString) {
        final Map<String, String> queryParams = new LinkedHashMap<>();
        final Matcher matcher = QUERY_PARAM_PATTERN.matcher(queryString);
        while (matcher.find()) {
            final String key = matcher.group(1);
            final String value = getOrDefault(matcher.group(2));
            queryParams.compute(key, joinWithCommaIfExistsOldValue(value));
        }

        if (queryParams.isEmpty()) {
            throw new ParseException();
        }

        return new Query(queryParams);
    }

    Map<String, String> getQueryParams() {
        return new LinkedHashMap<>(queryParams);
    }

    private static String getOrDefault(final String value) {
        if (value == null) {
            return "";
        }
        return value;
    }

    private static BiFunction<String, String, String> joinWithCommaIfExistsOldValue(final String value) {
        return (currentKey, oldValue) -> {
            if (oldValue == null) {
                return value;
            }
            return String.join(",", oldValue, value);
        };
    }

    @Override
    public String toString() {
        final List<String> keyValuePairs = new ArrayList<>();
        for (final Map.Entry<String, String> queryParam : queryParams.entrySet()) {
            final StringBuilder builder = new StringBuilder();
            builder.append(queryParam.getKey());
            builder.append('=');
            builder.append(queryParam.getValue());
            keyValuePairs.add(builder.toString());
        }
        return String.join("&", keyValuePairs);
    }
}

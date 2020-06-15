package http;

import model.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryStrings {
    private final List<QueryString> queryStrings;

    QueryStrings(List<QueryString> queryStrings) {
        this.queryStrings = queryStrings;
    }

    public static QueryStrings of(String path) {
        if ("".equals(path) || path == null) {
            return new QueryStrings(Collections.emptyList());
        }

        if (!path.contains("?")) {
            return new QueryStrings(Collections.emptyList());
        }

        final String[] pathValues = path.split("\\?");
        final String queryStringSource = pathValues[1];

        if (queryStringSource.contains("&")) {
            final String[] values = queryStringSource.split("&");
            return new QueryStrings(buildQueryStrings(values));
        }
        final String[] keyAndValue = queryStringSource.split("=");
        return new QueryStrings(Arrays.asList(new QueryString(keyAndValue)));
    }

    private static List<QueryString> buildQueryStrings(String[] values) {
        final List<QueryString> target = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            String[] keyAndValue = values[i].split("=");
            target.add(new QueryString(keyAndValue));
        }
        return target;
    }

    public List<QueryString> getQueryStrings() {
        return queryStrings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryStrings)) return false;
        QueryStrings that = (QueryStrings) o;
        return Objects.equals(getQueryStrings(), that.getQueryStrings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQueryStrings());
    }

    @Override
    public String toString() {
        return "QueryStrings{" +
                "queryStrings=" + queryStrings +
                '}';
    }

    public String getUserId() {
        return queryStrings.stream()
                .filter(QueryString::isUserId)
                .map(QueryString::getValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getPassword() {
        return queryStrings.stream()
                .filter(QueryString::isPassword)
                .map(QueryString::getValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getName() {
        return queryStrings.stream()
                .filter(QueryString::isName)
                .map(QueryString::getValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getEmail() {
        return queryStrings.stream()
                .filter(QueryString::isEmail)
                .map(QueryString::getValue)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

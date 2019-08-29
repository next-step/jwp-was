package model.http;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Query {
    public static final String PARAMETER_SPLITTER = "&";
    public static final Pattern PATTERN = Pattern.compile("(" + QueryParameter.PATTERN + PARAMETER_SPLITTER + ")*(" + QueryParameter.PATTERN + ")?");

    private List<QueryParameter> parameters;

    private Query(List<QueryParameter> queryParameters) {
        parameters = queryParameters;
    }

    public static Query ofEmpty() {
        return new Query(new ArrayList<>());
    }

    public static Query of(String queryString) {
        return new Query(parse(queryString));
    }

    public static Query of(List<QueryParameter> queryParameters) {
        return new Query(queryParameters);
    }

    private static List<QueryParameter> parse(String queryString) {
        if (!validatePattern(queryString)) throw new IllegalArgumentException("wrong query pattern");
        return Arrays.stream(queryString.split(PARAMETER_SPLITTER))
                .map(QueryParameter::of).collect(Collectors.toList());
    }

    private static boolean validatePattern(String queryString) {
        Matcher matcher = PATTERN.matcher(queryString);
        return matcher.find() && queryString.length() == matcher.group().length();
    }

    public Optional<QueryParameter> findByName(String name) {
        return parameters.stream()
                .filter(queryParameter -> queryParameter.isEqualName(name))
                .findFirst();
    }

    public Query addAll(Query query) {
        parameters.addAll(query.parameters);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return Objects.equals(parameters, query.parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }

    @Override
    public String toString() {
        return "Query{" +
                "parameters=" + parameters +
                '}';
    }
}

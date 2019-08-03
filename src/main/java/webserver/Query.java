package webserver;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Query {
    public static final String PARAMETER_SPLITTER = "&";
    public static final Pattern pattern = Pattern.compile("(" + QueryParameter.pattern + PARAMETER_SPLITTER + ")*(" + QueryParameter.pattern + ")?");

    private List<QueryParameter> parameters;

    private Query(List<QueryParameter> queryParameters) {
        parameters = queryParameters;
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
        Matcher matcher = pattern.matcher(queryString);
        return matcher.find() && queryString.length() == matcher.group().length();
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
}

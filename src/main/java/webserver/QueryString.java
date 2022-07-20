package webserver;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.Constant.KEY_VALUE_SPERATOR;
import static model.Constant.QUERY_STRING_SPERATOR;

public class QueryString {
    private final Map<String, String> parameter;

    public Map<String, String> getParameter() {
        return parameter;
    }

    public QueryString(String queryString) {
        this.parameter = StringUtils.isEmpty(queryString) ? Collections.emptyMap() : parse(queryString);
    }

    private Map<String, String> parse(String queryString) {
        return Stream.of(StringUtils.split(queryString, QUERY_STRING_SPERATOR))
                .map(keyValue -> StringUtils.split(keyValue, KEY_VALUE_SPERATOR))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(parameter, that.parameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter);
    }

    @Override
    public String toString() {
        return "QueryString{" +
                "parameter=" + parameter +
                '}';
    }
}

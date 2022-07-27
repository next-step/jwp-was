package webserver.http.request;

import com.github.jknack.handlebars.internal.lang3.StringUtils;
import exception.InvalidRequestException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static model.Constant.KEY_VALUE_SPERATOR;
import static model.Constant.QUERY_STRING_SPERATOR;

public class QueryString {
    private final Map<String, String> queryString;

    public QueryString(Map<String, String> queryString) {
        this.queryString = queryString;
    }

    public QueryString(String value) {
        this.queryString = StringUtils.isEmpty(value) ? Collections.emptyMap() : parse(value);
    }

    public QueryString(String... values) {
        if (values.length % 2 != 0) {
            throw new InvalidRequestException("QueryString");
        }

        Map<String, String> map = new HashMap<>();

        int i = 0;
        while (i < values.length) {
            map.put(values[i++], values[i++]);
        }

        this.queryString = map;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    private Map<String, String> parse(String value) {
        return Stream.of(StringUtils.split(value, QUERY_STRING_SPERATOR))
                .map(keyValue -> StringUtils.split(keyValue, KEY_VALUE_SPERATOR))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryString that = (QueryString) o;
        return Objects.equals(queryString, that.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(queryString);
    }
}

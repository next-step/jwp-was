package webserver.http;

import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class RequestQuery {

    static final RequestQuery EMPTY = new RequestQuery(Collections.emptyMap());

    private static final String SEPARATOR = "&";

    private final Map<String, String> queryString;

    private RequestQuery(final Map<String, String> queryString) {
        this.queryString = queryString;
    }

    public static RequestQuery of(final String rawQueryString) {
        if (StringUtils.isBlank(rawQueryString)) {
            return EMPTY;
        }

        return Arrays.stream(rawQueryString.split(SEPARATOR))
                .map(RequestQueryValue::of)
                .collect(collectingAndThen(toMap(RequestQueryValue::getKey, RequestQueryValue::getValue),
                        RequestQuery::new));
    }

    public String getString(final String key) {
        return queryString.get(key);
    }

    @Override
    public String toString() {
        return "RequestQuery{" +
                "queryString=" + queryString +
                '}';
    }
}

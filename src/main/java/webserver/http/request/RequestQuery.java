package webserver.http.request;

import utils.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class RequestQuery {

    private static final String SEPARATOR = "&";

    private final Map<String, String> requestQuery;

    private RequestQuery(final Map<String, String> requestQuery) {
        this.requestQuery = requestQuery;
    }

    public static RequestQuery of(final String rawRequestQuery) {
        if (StringUtils.isBlank(rawRequestQuery)) {
            return empty();
        }

        return Arrays.stream(rawRequestQuery.split(SEPARATOR))
                .map(RequestQueryValue::of)
                .collect(collectingAndThen(toMap(RequestQueryValue::getKey, RequestQueryValue::getValue),
                        RequestQuery::new));
    }

    public static RequestQuery empty() {
        return new RequestQuery(new HashMap<>());
    }

    public String getString(final String key) {
        return requestQuery.get(key);
    }

    public boolean isEmpty() {
        return requestQuery.isEmpty();
    }

    void join(final RequestQuery other) {
        requestQuery.putAll(other.requestQuery);
    }

    @Override
    public String toString() {
        return "RequestQuery{" +
                "requestQuery=" + requestQuery +
                '}';
    }
}

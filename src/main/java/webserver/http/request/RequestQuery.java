package webserver.http.request;

import utils.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toMap;

public class RequestQuery {

    static final RequestQuery EMPTY = new RequestQuery(Collections.emptyMap());

    private static final String SEPARATOR = "&";

    private final Map<String, String> requestQuery;

    private RequestQuery(final Map<String, String> requestQuery) {
        this.requestQuery = requestQuery;
    }

    public static RequestQuery of(final String rawRequestQuery) {
        if (StringUtils.isBlank(rawRequestQuery)) {
            return EMPTY;
        }

        return Arrays.stream(rawRequestQuery.split(SEPARATOR))
                .map(RequestQueryValue::of)
                .collect(collectingAndThen(toMap(RequestQueryValue::getKey, RequestQueryValue::getValue),
                        RequestQuery::new));
    }

    public String getString(final String key) {
        return requestQuery.get(key);
    }

    @Override
    public String toString() {
        return "RequestQuery{" +
                "requestQuery=" + requestQuery +
                '}';
    }
}

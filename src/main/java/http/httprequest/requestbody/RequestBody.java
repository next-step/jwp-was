package http.httprequest.requestbody;

import org.springframework.util.CollectionUtils;
import utils.QueryStringParser;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RequestBody {
    private static final Map<String, String> EMPTY_MAP = Collections.emptyMap();

    Map<String, String> body;

    public RequestBody(Map<String, String> body) {
        if(CollectionUtils.isEmpty(body)) {
            body = EMPTY_MAP;
        }
        this.body = body;
    }

    public RequestBody(String body) {
        this(QueryStringParser.parse(body));
    }

    public static RequestBody empty() {
        return new RequestBody(EMPTY_MAP);
    }

    public Optional<String> getValue(String key) {
        if (body.containsKey(key)) {
            return Optional.of(body.get(key));
        }

        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBody that = (RequestBody) o;
        return body.equals(that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}

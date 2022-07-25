package webserver.request;

import java.util.Map;
import java.util.Objects;
import webserver.utils.QueryStrings;

public class HttpBody {

    private final QueryStrings queryStrings;

    public HttpBody() {
        this("");
    }

    public HttpBody(String body) {
        validate(body);
        this.queryStrings = QueryStrings.of(body);
    }

    private void validate(String body) {
        Objects.requireNonNull(body);
    }

    public Map<String, String> getBodyMap() {
        return queryStrings.map();
    }

}

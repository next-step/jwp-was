package webserver.request;

import http.request.QueryParameters;
import utils.QueryUrlParser;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class RequestBody {

    private Map<String, String> body;

    private RequestBody(Map<String, String> body) {
        this.body = body;
    }

    public static RequestBody from(String url) {
        return new RequestBody(QueryUrlParser.toMap(url));
    }

    public static RequestBody empty() {
        return new RequestBody(Collections.emptyMap());
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(this.body.get(key));
    }
}

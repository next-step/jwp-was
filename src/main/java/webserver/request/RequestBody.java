package webserver.request;

import utils.UrlQueryUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class RequestBody {

    private final Map<String, String> body;

    private RequestBody(Map<String, String> body) {
        this.body = Collections.unmodifiableMap(body);
    }

    public static RequestBody from(String string) {
        return new RequestBody(UrlQueryUtils.toMap(string));
    }

    static RequestBody empty() {
        return new RequestBody(Collections.emptyMap());
    }

    Optional<String> value(String key) {
        if (body.containsKey(key)) {
            return Optional.ofNullable(body.get(key));
        }
        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestBody that = (RequestBody) o;
        return Objects.equals(body, that.body);
    }
}

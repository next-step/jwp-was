package webserver.http.request;

import utils.UrlQueryUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

class QueryString {

    private final Map<String, String> parameters;

    private QueryString(Map<String, String> parameters) {
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    static QueryString from(String string) {
        return new QueryString(UrlQueryUtils.toMap(string));
    }

    static QueryString empty() {
        return new QueryString(Collections.emptyMap());
    }

    Optional<String> value(String property) {
        if (parameters.containsKey(property)) {
            return Optional.ofNullable(parameters.get(property));
        }
        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        QueryString that = (QueryString) o;
        return Objects.equals(parameters, that.parameters);
    }

    @Override
    public String toString() {
        return "QueryString{" +
                "parameters=" + parameters +
                '}';
    }
}

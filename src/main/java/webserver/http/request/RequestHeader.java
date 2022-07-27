package webserver.http.request;

import webserver.http.HttpHeaders;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RequestHeader {

    private static final String DELIMITER = ":";
    private static final String DEFAULT_CONTENT_LENGTH = "0";

    private final Map<String, String> headers;

    private RequestHeader(Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    public static RequestHeader from(Map<String, String> headers) {
        return new RequestHeader(Objects.requireNonNullElse(headers, Collections.emptyMap()));
    }

    public static RequestHeader from(List<String> headerStrings) {
        if (headerStrings == null) {
            return empty();
        }
        return from(
                headerStrings.stream()
                        .filter(string -> string.contains(DELIMITER))
                        .map(RequestHeader::parseToEntry)
                        .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }

    private static RequestHeader empty() {
        return from(Collections.emptyMap());
    }

    private static Map.Entry<String, String> parseToEntry(String keyValue) {
        String[] splitKeyValue = keyValue.split(DELIMITER, 2);
        return Map.entry(splitKeyValue[0].trim(), splitKeyValue[1].trim());
    }

    public Optional<String> value(String key) {
        if (headers.containsKey(key)) {
            return Optional.ofNullable(headers.get(key));
        }
        return Optional.empty();
    }

    public int contentLength() {
        return Integer.parseInt(headers.getOrDefault(HttpHeaders.CONTENT_LENGTH, DEFAULT_CONTENT_LENGTH));
    }

    public String cookie() {
        return headers.getOrDefault(HttpHeaders.COOKIE, "");
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestHeader that = (RequestHeader) o;
        return Objects.equals(headers, that.headers);
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "headers=" + headers +
                '}';
    }
}

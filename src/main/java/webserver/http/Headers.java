package webserver.http;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Headers {
    private static final String HEADER_DELIMITER = ": ";

    private static final int NAME_IDX = 0;

    private static final int FIELD_IDX = 1;

    private final Map<String, String> headers;

    Headers(Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    static Headers parseOf(List<String> headerLines) {
        Map<String, String> headers = headerLines.stream()
                .filter((headerLine) -> !headerLine.isEmpty())
                .map(headerLine -> headerLine.split(HEADER_DELIMITER))
                .collect(Collectors.toMap(entry -> entry[NAME_IDX], entry -> entry[FIELD_IDX]));

        return new Headers(headers);
    }

    public static Headers of(Map<String, String> headers) {
        return new Headers(headers);
    }

    public String getValue(String name) {
        return headers.get(name);
    }

    List<String> getMessages() {
        return headers.entrySet()
                .stream()
                .map(entry -> entry.getKey() + HEADER_DELIMITER + entry.getValue())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Headers other = (Headers) o;
        return Objects.equals(headers, other.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }

    @Override
    public String toString() {
        return headers.toString();
    }
}

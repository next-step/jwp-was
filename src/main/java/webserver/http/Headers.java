package webserver.http;

import java.util.*;
import java.util.stream.Collectors;

public class Headers {
    private static final String HEADER_DELIMITER = ": ";

    private static final int NAME_IDX = 0;

    private static final int FIELD_IDX = 1;

    private final List<Header> headers;

    Headers() {
        this(new ArrayList<>());
    }

    Headers(List<Header> headers) {
        this.headers = headers;
    }

    public static Headers parseOf(List<String> headerLines) {
        List<Header> headers = headerLines.stream()
                .filter((headerLine) -> !headerLine.isEmpty())
                .map(headerLine -> headerLine.split(HEADER_DELIMITER))
                .map(entry -> new Header(entry[NAME_IDX], entry[FIELD_IDX]))
                .collect(Collectors.toUnmodifiableList());

        return new Headers(headers);
    }

    public static Headers of(Header... header) {
        return new Headers(Arrays.asList(header));
    }

    public String getValue(String name) {
        return this.headers
                .stream()
                .filter(header -> header.getName().equalsIgnoreCase(name))
                .findAny()
                .orElse(Header.EMPTY)
                .getValue();
    }

    List<String> getMessages() {
        return headers
                .stream()
                .map(entry -> entry.getName() + HEADER_DELIMITER + entry.getValue())
                .collect(Collectors.toUnmodifiableList());
    }

    void addHeader(String name, String value) {
        this.headers.add(new Header(name, value));
    }

    void setHeader(String name, String value) {
        headers.removeIf(header -> header.equalsName(name));

        this.headers.add(new Header(name, value));
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

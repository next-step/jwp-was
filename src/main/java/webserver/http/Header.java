package webserver.http;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Header {
    private final Map<String, String> header;

    public Header(List<String> headers) {
        this.header = headers.stream()
                .map(request -> request.split(": "))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

    public Header(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Header header1 = (Header) o;
        return Objects.equals(header, header1.header);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header);
    }
}

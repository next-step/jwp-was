package webserver.http;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Header {
    private final Map<String, String> headers;

    public Header(List<String> headerMap) {
        this.headers = headerMap.stream()
                .map(request -> request.split(": "))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
    }

    public Header(Map<String, String> header) {
        this.headers = header;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getContentLength() {
        return StringUtils.isEmpty(headers.get("Content-Length")) ? 0 : Integer.parseInt(headers.get("Content-Length"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Header header1 = (Header) o;
        return Objects.equals(headers, header1.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }
}

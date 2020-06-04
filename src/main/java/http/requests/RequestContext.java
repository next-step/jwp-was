package http.requests;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestContext {

    public static final String REQUEST_HEADER_DELIMITER = ":";
    private final RequestLine requestLine;
    private final Map<String, String> requestHeaders;

    public RequestContext(String rawRequestLine, List<String> rawRequestHeaders) {
        this.requestLine = new RequestLine(rawRequestLine);
        this.requestHeaders = rawRequestHeaders.stream()
                .map(this::splitRequestHeaderKeyAndValue)
                .collect(Collectors.toMap(
                        AbstractMap.SimpleImmutableEntry::getKey,
                        AbstractMap.SimpleImmutableEntry::getValue)
                );
    }

    private AbstractMap.SimpleImmutableEntry<String, String> splitRequestHeaderKeyAndValue(String header) {
        final String[] split = header.split(REQUEST_HEADER_DELIMITER);
        return new AbstractMap.SimpleImmutableEntry<>(split[0].trim(), split[1].trim());
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "requestLine=" + requestLine +
                ", requestHeaders=" + requestHeaders +
                '}';
    }
}

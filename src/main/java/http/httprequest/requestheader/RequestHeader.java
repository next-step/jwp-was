package http.httprequest.requestheader;

import http.httpresponse.HttpHeaders;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestHeader {
    private static final String KEY_VALUE_DELIMITER = ": ";
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private final Map<String, String> headers;

    private RequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader from(Map<String, String> headers) {
        return new RequestHeader(Objects.requireNonNullElse(headers, Collections.emptyMap()));
    }

    public static RequestHeader from(List<String> headerStrings) {
        if(CollectionUtils.isEmpty(headerStrings)) {
            return new RequestHeader(Collections.emptyMap());
        }

        Map<String, String> headers = headerStrings.stream()
                .filter(string -> string.contains(KEY_VALUE_DELIMITER))
                .map(RequestHeader::parseToEntry)
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

        return from(headers);
    }

    private static Map.Entry<String, String> parseToEntry(String keyValue) {
        String[] keyValuePair = keyValue.split(KEY_VALUE_DELIMITER);
        return Map.entry(keyValuePair[KEY_INDEX].trim(), keyValuePair[VALUE_INDEX].trim());
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault(HttpHeaders.CONTENT_LENGTH, "0"));
    }

    public String getCookie() {
        return headers.getOrDefault(HttpHeaders.COOKIE, "");
    }

    public RequestHeader delete(String header) {
        HashMap<String, String> newHeader = new HashMap<>(headers);
        newHeader.remove(header);
        return new RequestHeader(newHeader);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestHeader that = (RequestHeader) o;
        return headers.equals(that.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers);
    }

}

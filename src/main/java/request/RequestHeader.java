package request;

import constant.HttpHeader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RequestHeader {
    private static final String DELIMITER = ": ";

    private Map<String, String> headers;

    public RequestHeader(Map<String, String> headers) {
        this.headers = Collections.unmodifiableMap(headers);
    }

    public static RequestHeader from(List<String> lines) {
        return new RequestHeader(lines.stream()
            .filter(line -> line.contains(DELIMITER))
            .map(RequestHeader::parseToEntry)
            .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private static Map.Entry<String, String> parseToEntry(String keyValue) {
        String[] splitKeyValue = keyValue.split(DELIMITER);
        return Map.entry(splitKeyValue[0].trim(), splitKeyValue[1].trim());
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public RequestCookie getCookie() {
        return RequestCookie.of(headers.get(HttpHeader.COOKIE.getValue()));
    }

    public int getContentLength() {
        String contentLength = getHeader(HttpHeader.CONTENT_LENGTH.getValue());
        if (Objects.isNull(contentLength)) {
            return 0;
        }
        return Integer.parseInt(contentLength);
    }


}

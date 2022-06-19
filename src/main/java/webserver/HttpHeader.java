package webserver;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HttpHeader {

    private final static String HEADER_DELIMITER = ": ";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> header;

    private HttpHeader(Map<String, String> header) {
        this.header = header;
    }

    public static HttpHeader from(List<String> request) {
        Map<String, String> collect = request.stream()
                .filter(line -> !line.isEmpty())
                .map(line -> line.split(HEADER_DELIMITER))
                .collect(Collectors.toUnmodifiableMap(
                        keyValue -> keyValue[0],
                        keyValue -> keyValue[1]
                ));

        return new HttpHeader(collect);
    }

    public String get(String key) {
        return Optional.ofNullable(header.get(key))
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getContentLength() {
        String contentLength = header.get(CONTENT_LENGTH);
        try {
            return Integer.parseInt(contentLength);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isSetCookie() {
        return header.containsKey("Cookie") &&
                "logined=true".equals(get("Cookie"));
    }
}

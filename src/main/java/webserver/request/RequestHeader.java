package webserver.request;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestHeader {
    private final Map<String, String> headers = new LinkedHashMap<>();

    private RequestHeader() {}

    private RequestHeader put(String key, String value) {
        headers.put(key, value);
        return this;
    }

    private RequestHeader put(String[] headerTokens) {
        if (headerTokens.length == 2) {
            put(headerTokens[0], headerTokens[1]);
        }
        return this;
    }

    public static RequestHeader from(List<String> headerLines) {
        return headerLines.stream().map(
                headerLine -> headerLine.split(": ")
        ).reduce(
                new RequestHeader(),
                (acc, headerTokens) -> acc.put(headerTokens),
                (__, requestHeader) -> requestHeader
        );
    }

    public String getContentType() {
        return headers.getOrDefault("Accept", "")
                .split(",")[0];
    }

    public int getContentLength() {
        return Integer.parseInt(
                headers.getOrDefault("Content-Length", "0")
        );
    }

    public String getCookie() {
        return headers.getOrDefault("Set-Cookie", "");
    }
}

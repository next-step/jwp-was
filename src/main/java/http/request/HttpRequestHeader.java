package http.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpRequestHeader {

    private Map<String, String> headerMap;

    private static final String CONTENT_LENGTH = "Content-Length";

    public HttpRequestHeader(Map<String, String> headerMap) {
        this.headerMap = headerMap;
        if (this.headerMap == null) this.headerMap = new HashMap<>();
    }

    public boolean hasBody() {
        return getContentLength() > 0;
    }

    public int getContentLength() {
        String contentLength = getHeader(CONTENT_LENGTH);
        return Integer.parseInt(Objects.toString(contentLength, "0"));
    }

    public String getHeader(String key) {
        return headerMap.get(key);
    }
}

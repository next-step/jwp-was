package webserver.http.response.headers;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {

    private final Map<String, String> headers = new HashMap<>();

    private HttpResponseHeader() {
    }

    public static HttpResponseHeader fromLocation(final String path) {
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader();

        httpResponseHeader.headers.put("Location", path);

        return httpResponseHeader;
    }

    public static HttpResponseHeader fromContent(final String contentType, final int length) {
        HttpResponseHeader httpResponseHeader = new HttpResponseHeader();

        httpResponseHeader.headers.put("content-type", contentType);
        httpResponseHeader.headers.put("content-length", String.valueOf(length));

        return httpResponseHeader;
    }

    public static HttpResponseHeader empty() {
        return new HttpResponseHeader();
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
}

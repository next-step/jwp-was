package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpHeader {
    Map<String, String> headers = new HashMap<>();

    public HttpHeader() {
    }

    public void loadHeader(String headerString) {
        String headerName = headerString.split(":")[0];
        if (headerName.isEmpty()) {
            return;
        }
        String headerValue = headerString.split(":")[1].trim();
        headers.put(headerName, headerValue);
    }

    public String getHeaderValue(String headerName) {
        return headers.get(headerName);
    }
}

package webserver.http;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {

    private Map<String, String> headers = new HashMap<>();

    public void addHeader(String requestHeaderLine) {
        if (requestHeaderLine == null) {
            return;
        }

        String[] splitedHeader = requestHeaderLine.split(": ");
        if (splitedHeader.length == 2) {
            this.headers.put(splitedHeader[0].trim(), splitedHeader[1].trim());
        }
    }

    public void addHeader(String contentTypeKey, String contentTypeValue) {
        this.headers.put(contentTypeKey, contentTypeValue);
    }

    public String getHeader(String contentTypeKey) {
        return this.headers.getOrDefault(contentTypeKey, "");
    }

    public int getContentLength() {
        int defaultContentLength = 0;
        String contentLength = headers.get("Content-Length");
        if (contentLength != null) {
            defaultContentLength = Integer.parseInt(contentLength);
        }
        return defaultContentLength;
    }
}

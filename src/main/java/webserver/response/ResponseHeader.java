package webserver.response;

import java.util.LinkedHashMap;
import java.util.Map;
import webserver.common.Protocol;

public class ResponseHeader {
    private final Protocol protocol;
    private final HttpStatus httpStatus;
    private final Map<String, String> headers = new LinkedHashMap<>();

    public ResponseHeader(Protocol protocol, HttpStatus httpStatus) {
        this.protocol = protocol;
        this.httpStatus = httpStatus;
    }

    private ResponseHeader put(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public ResponseHeader setContentType(String contentType) {
        return put("Content-Type", contentType);
    }

    public ResponseHeader setContentLength(int contentLength) {
        return put("Content-Length", Integer.toString(contentLength));
    }

    public ResponseHeader setLocation(String location) {
        return put("Location", location);
    }

    public ResponseHeader setCookie(String cookie) {
        return put("Set-Cookie", cookie);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s \r\n", protocol.toString(), httpStatus.toString()));
        for (Map.Entry entry : headers.entrySet()) {
            sb.append(String.format("%s: %s \r\n", entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }
}

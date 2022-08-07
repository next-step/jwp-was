package webserver.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResponseHeader {

    private HttpStatus httpStatus;
    private Map<String, String> headers = new HashMap<>();

    public ResponseHeader() {
        this.httpStatus = HttpStatus.OK;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Set<String> getHeaderKeys() {
        return headers.keySet();
    }

    public void updateStatus(HttpStatus status) {
        this.httpStatus = status;
    }

    public String getStatusMessage() {
        return httpStatus.toString();
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}

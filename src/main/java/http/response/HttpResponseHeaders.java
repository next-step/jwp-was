package http.response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponseHeaders {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final Map<String, String> values;

    public HttpResponseHeaders(Map<String, String> headers, int bodyLength) {
        this.values = putDefaultHeader(headers, bodyLength);
    }

    private Map<String, String> putDefaultHeader(Map<String, String> headers, int bodyLength) {
        var newHeaders = new HashMap<>(headers);

        newHeaders.putIfAbsent(CONTENT_TYPE, "text/html;charset=utf-8");
        newHeaders.putIfAbsent(CONTENT_LENGTH, String.valueOf(bodyLength));

        return newHeaders;
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return Set.copyOf(values.entrySet());
    }

    public Map<String, String> getHeaders() {
        return new HashMap<>(values);
    }

    public boolean isMarkDown() {
        if (!values.containsKey(CONTENT_TYPE)) {
            return false;
        }

        return values.get(CONTENT_TYPE)
            .startsWith("text/html");
    }

    public void write(DataOutputStream dos) throws IOException {
        for (Map.Entry<String, String> entry : values.entrySet()) {
            dos.writeBytes(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
        }
    }
}

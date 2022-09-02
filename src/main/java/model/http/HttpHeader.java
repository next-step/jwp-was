package model.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpHeader {

    public static final String DELIMITER = ": ";

    private Map<String, String> headers = new HashMap<>();

    public HttpHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public HttpHeader(List<String> headers) {
        this.headers = headers.stream()
                .map(header -> header.split(DELIMITER))
                .collect(Collectors.toMap(item -> item[0], item -> item[1].strip()));
    }

    public String getValue(String name) {
        return headers.get(name);
    }

    public int getValueToInt(String name) {
        final String value = getValue(name);
        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public void writeOutput(DataOutputStream dos) throws IOException {
        headers.entrySet().stream()
                .forEach(entry -> {
                    try {
                        dos.writeBytes(entry.getKey() + DELIMITER + entry.getValue() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        dos.writeBytes("\n");
    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "headers=" + headers +
                '}';
    }

    public static class HttpHeaderBuilder {
        private static Map<String, String> headers = new HashMap<>();

        public HttpHeaderBuilder location(String location) {
            headers.put("Location", location);
            return this;
        }

        public HttpHeaderBuilder contentLength(int length) {
            headers.put("Content-Length", String.valueOf(length));
            return this;
        }

        public HttpHeaderBuilder contentType(String contentType) {
            headers.put("Content-Type", contentType + ";charset=utf-8");
            return this;
        }

        public HttpHeaderBuilder cookie(String name, String value) {
            headers.put("Set-Cookie", name + "=" + value);
            return this;
        }

        public HttpHeader build() {
            return new HttpHeader(headers);
        }
    }
}
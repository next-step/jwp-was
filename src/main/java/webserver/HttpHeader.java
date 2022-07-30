package webserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HttpHeader {
    private final Map<String, String> headers = new HashMap<>();

    public HttpHeader(String line) {
        String headerKey = line.split(":")[0];
        if (headerKey.isEmpty()) {
            return;
        }
        String headerValue = line.split(":")[1];

        if (Objects.equals(headerKey, "Cookie")) {
            String logined = HttpHeaderParser.isLogin(headerValue);
            headers.put("logined", logined);
        }

        if (!Objects.equals(headerKey, "Cookie")) {
            headers.put(headerKey, headerValue);
        }
    }

    public int getContentLength() {
        if (headers.containsKey("Content-Length")) {
            return Integer.parseInt(headers.get("Content-Length"));
        }
        return 0;
    }

    public boolean isLogin() {
        return Boolean.parseBoolean(headers.get("logined"));
    }
}

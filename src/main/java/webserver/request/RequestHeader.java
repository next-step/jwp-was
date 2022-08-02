package webserver.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RequestHeader {
    private final Map<String, String> headers = new HashMap<>();

    public RequestHeader(String line) {
        String headerKey = line.split(":")[0];
        if (headerKey.isEmpty()) {
            return;
        }
        String headerValue = line.split(":")[1].trim();

        if (Objects.equals(headerKey, "Cookie")) {
            String logined = RequestHeaderParser.isLogin(headerValue);
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

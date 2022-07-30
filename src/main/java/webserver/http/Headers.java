package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Headers {
    private Map<String, String> headers;

    public Headers() {
        this(new HashMap<>());
    }

    public Headers(Map<String, String> headers) {
        this.headers = headers;
    }

    public Headers(BufferedReader br) throws IOException {
        this.headers = new HashMap<>();
        String line = br.readLine();
        while (!line.equals("")) {
            this.putHeader(line);
            line = br.readLine();
        }
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    public void putHeader(String line) {
        String[] tokens = line.split(":");
        headers.put(tokens[0].trim(), tokens[1].trim());
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public int getBodySize() {
        return Integer.parseInt(headers.get("Content-Length"));
    }

    public boolean isLogin() {
        String cookie = headers.get("Cookie");
        if (cookie != null || cookie.isEmpty()) {
            return cookie.contains("logined=true");
        }
        return false;
    }
}

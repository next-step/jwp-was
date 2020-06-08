package http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Cookies {
    private static final String SET_COOKIE_FORMAT = "Set-Cookie: %s=%s; Path=%s \r\n";

    private final Map<String, String> cookies = new HashMap<>();

    public Cookies() {}

    public void setCookie(final String key, final String value) {
        cookies.put(key, value);
    }

    public String getCookie(final String key) {
        return cookies.get(key);
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public void writeCookies(final DataOutputStream dataOutputStream) throws IOException {
        for (String key : cookies.keySet()) {
            dataOutputStream.writeBytes(String.format(SET_COOKIE_FORMAT, key, cookies.get(key), "/"));
        }
    }
}

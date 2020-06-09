package http;

import static http.HeaderName.RESPONSE_COOKIE;

import http.session.HttpSessionStorage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpUtils;

public class Cookie {

    private static final Logger logger = LoggerFactory.getLogger(Cookie.class);

    private static final String COOKIE_SPLITTER = ";";
    private static final String KEY_VALUE_SPLITTER = "=";

    private final Map<String, String> cookies;

    private Cookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public static Cookie from(String cookieLine) {
        return new Cookie(HttpUtils.getPairs(cookieLine, COOKIE_SPLITTER, KEY_VALUE_SPLITTER));
    }

    public String get(String key) {
        return this.cookies.get(key);
    }

    public void put(String name, String value) {
        this.cookies.put(name, value);
    }

    public void response(OutputStream out) {
        DataOutputStream dos = new DataOutputStream(out);
        this.cookies.forEach((name, value) -> {
            try {
                dos.writeBytes(
                    String.format("%s: %s=%s; Path=/\r\n", RESPONSE_COOKIE.getKey(), name, value));
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

    HttpSession getHttpSession(HttpSessionStorage httpSessionStorage) {
        String sessionId = this.cookies.get(HttpSessionStorage.SESSION_ID_NAME);
        return httpSessionStorage.getHttpSession(sessionId)
            .orElseGet(() -> httpSessionStorage.newHttpSession());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cookie cookie = (Cookie) o;
        return Objects.equals(cookies, cookie.cookies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cookies);
    }


}

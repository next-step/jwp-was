package http;

import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookies {
    private static final String COOKIE_TOKENIZER = ";";
    private static final String COOKIE_NAME_VALUE_TOKENIZER = "=";

    private Map<String, String> cookies;
    private String path = Strings.EMPTY;

    public Cookies() {
        cookies = new HashMap<>();
    }

    public Cookies(String values) {
        cookies = new HashMap<>();
        for (String cookie : values.split(COOKIE_TOKENIZER)) {
            String[] c = cookie.split(COOKIE_NAME_VALUE_TOKENIZER);
            if (c.length != 2) {
                throw new RuntimeException("유효하지 않은 Cookie 헤더임. header :: [ Cookie: " + values + "]");
            }
            String cookieName = c[0].trim();
            String cookieValue = c[1].trim();
            cookies.put(cookieName, cookieValue);
        }
    }

    public String getValue(String name) {
        String defaultValue = "";
        return cookies.getOrDefault(name, defaultValue);
    }

    public void addCookie(String cookieName, String cookieValue) {
        cookies.put(cookieName, cookieValue);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String stringify() {
        String cookiesStr = cookies.keySet().stream()
                .map(cookieName -> (cookieName + "="+cookies.get(cookieName)))
                .collect(Collectors.joining("; "));

        if (!Strings.EMPTY.equals(path)) {
            cookiesStr += "; Path=" + path;
        }
        return cookiesStr;
    }

    public boolean isEmpty() {
        return cookies.isEmpty();
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookies=" + cookies +
                '}';
    }
}

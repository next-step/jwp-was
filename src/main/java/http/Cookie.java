package http;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private static final String COOKIE_TOKENIZER = ";";
    private static final String COOKIE_NAME_VALUE_TOKENIZER = "=";

    private Map<String, String> cookies = new HashMap<>();

    public Cookie(String values) {
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

    @Override
    public String toString() {
        return "Cookie{" +
                "cookies=" + cookies +
                '}';
    }
}

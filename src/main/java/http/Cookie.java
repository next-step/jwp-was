package http;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private static final String COOKIE_HEADER_START_CONDITION = "Cookie: ";
    private static final String HEADER_NAME_VALUE_TOKENIZER = ":";
    private static final String COOKIE_TOKENIZER = ";";
    private static final String COOKIE_NAME_VALUE_TOKENIZER = "=";

    private Map<String, String> cookies = new HashMap<>();

    public Cookie(String header) {
        if (header == null || !header.startsWith(COOKIE_HEADER_START_CONDITION)) {
            throw new RuntimeException("쿠키 관련 헤더가 아님. header :: [" + header + "]");
        }

        String values = header.split(HEADER_NAME_VALUE_TOKENIZER, 2)[1].trim();
        for (String cookie : values.split(COOKIE_TOKENIZER)) {
            String[] c = cookie.split(COOKIE_NAME_VALUE_TOKENIZER);
            if (c.length != 2) {
                throw new RuntimeException("유효하지 않은 Cookie 헤더임. header :: [" + header + "]");
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

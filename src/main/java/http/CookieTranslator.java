package http;

import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

public class CookieTranslator {

    private static final String SEPARATOR = ";";
    private static final String SEPARATOR_COOKIE_KEY_VALUE = "=";

    private Map<String, String> cookies;

    public CookieTranslator(final String values) {
        cookies = new HashMap<>();
        if (Strings.isNotBlank(values)) {
            create(values.split(SEPARATOR));
        }
    }

    private void create(String[] cookieValues) {
        for (final String cookieValue : cookieValues) {
            String[] cookiesKeyAndValue = cookieValue.split(SEPARATOR_COOKIE_KEY_VALUE);
            cookies.put(cookiesKeyAndValue[0].trim(), cookiesKeyAndValue[1].trim());
        }
    }

    public String getSessionId() {
        return cookies.get("CUSTOM_SESSION_ID");
    }
}

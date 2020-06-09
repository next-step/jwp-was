package http;

public class CookieTranslator {

    private static final String SEPARATOR = ";";
    private static final String SEPARATOR_COOKIE_KEY_VALUE = "=";

    private String[] cookieValues;

    public CookieTranslator(final String cookieValues) {
        this.cookieValues = cookieValues.split(SEPARATOR);
    }

    public boolean isLogined() {
        for (final String cookieValue : cookieValues) {
            String[] cookies = cookieValue.split(SEPARATOR_COOKIE_KEY_VALUE);
            if (cookies[0].trim().equals("logined") && cookies[1].trim().equals("true")) {
                return true;
            }
        }
        return false;
    }

    public String[] getCookieValues() {
        return cookieValues;
    }
}

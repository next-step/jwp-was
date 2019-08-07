package webserver.http;

public class HttpCookie {

    private final String name;

    private final String value;

    public HttpCookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getCookieNameAndValue() {
        return this.name + HttpCookies.COOKIE_SPLIT_SIGN + this.value;
    }
}

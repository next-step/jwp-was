package http;

/**
 * Created by iltaek on 2020/06/09 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public enum HttpHeaderName {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    COOKIE("Cookie"),
    SET_COOKIE("Set-Cookie");

    private final String name;

    HttpHeaderName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

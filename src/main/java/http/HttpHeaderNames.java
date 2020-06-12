package http;

/**
 * Created by iltaek on 2020/06/11 Blog : http://blog.iltaek.me Github : http://github.com/iltaek
 */
public enum HttpHeaderNames {

    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    LOCATION("Location"),
    COOKIE("Cookie"),
    SET_COOKIE("Set-Cookie"),
    HOST("Host"),
    CONNECTION("Connection"),
    ACCEPT("Accept");

    private final String name;

    HttpHeaderNames(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

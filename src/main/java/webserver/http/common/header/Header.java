package webserver.http.common.header;

/**
 * @author : yusik
 * @date : 2019-08-10
 */
public enum Header {

    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    SET_COOKIE("Set-Cookie"),
    COOKIE("Cookie"),
    ;

    private final String name;

    Header(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

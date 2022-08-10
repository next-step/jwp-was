package webserver.http;

public class HttpCookie {
    private static final String COOKIE_DELIMITER = ";";
    private Params params;

    public HttpCookie(String value) {
        this.params = Params.of(value, COOKIE_DELIMITER);
    }

    public String getCookie(String value) {
        return params.getParam(value);
    }

}

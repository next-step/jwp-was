package webserver.request;

public class RequestURI {
    private static final String DELIMITER = "\\?";

    private final String path;
    private final String queryString;

    public RequestURI(String uri) {
        this.path = uri.split(DELIMITER)[0];
        this.queryString = uri.split(DELIMITER).length == 2 ? uri.split(DELIMITER)[1] : null;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }
}

package webserver.http;

public class RequestLine {
    private String method;
    private String path;
    private QueryString queryString;

    private RequestLine(String method, String path) {
        this.method = method;
        this.path = path;
        this.queryString = QueryString.parse(path);
    }

    public static RequestLine parse(String path) {
        String[] values = path.split(" ");
        return new RequestLine(values[0], values[1]);
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public QueryString getQueryString() {
        return this.queryString;
    }
}

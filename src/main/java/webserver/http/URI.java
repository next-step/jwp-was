package webserver.http;

public class URI {
    private String path;
    private QueryString queryString;

    private URI(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static URI parse(String path) {
        return new URI(path, QueryString.parse(path));
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }
}

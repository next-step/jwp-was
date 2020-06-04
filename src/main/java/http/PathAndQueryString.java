package http;

public class PathAndQueryString {

    private QueryString queryString;
    private String path;

    public PathAndQueryString(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public String getPath() {
        return this.path;
    }

    public QueryString getQueryString() {
        return this.queryString;
    }

}

package http;

public class PathAndQueryString {

    private String queryString;
    private String path;

    public PathAndQueryString(String path, String queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public String getPath() {
        return this.path;
    }

    public String getQeuryString() {
        return this.queryString;
    }
}

package model.request;

public class Path {
    private final String path;
    private final QueryString queryString;

    public Path(String path) {
        this.queryString = new QueryString(path);
        this.path = queryString.removeQueryString(path);
    }

    public Boolean hasQueryString() {
        return queryString.getQueryString() != null;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        return queryString.getParameter(key);
    }
}

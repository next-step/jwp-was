package model.request;

public class Path {
    private final String path;
    private final QueryStrings queryStrings;

    public Path(String path) {
        this.queryStrings = new QueryStrings(path);
        this.path = queryStrings.removeQueryString(path);
    }

    public Boolean hasQueryString() {
        return queryStrings.getQueryString() != null;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        return queryStrings.getParameter(key);
    }
}

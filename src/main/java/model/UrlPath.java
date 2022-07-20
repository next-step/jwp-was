package model;

public class UrlPath {

    private final String path;

    private final QueryParameter queryParameter;

    public UrlPath(String path, QueryParameter queryParameter) {
        this.path = path;
        this.queryParameter = queryParameter;
    }

    public String getPath() {
        return path;
    }

    public QueryParameter getQueryParameter() {
        return queryParameter;
    }

}

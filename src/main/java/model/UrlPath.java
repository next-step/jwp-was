package model;

import java.util.Objects;

public class UrlPath {

    private final String path;

    private QueryParameter queryParameter;

    public UrlPath(String path) {
        this.path = path;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlPath urlPath = (UrlPath) o;
        return path.equals(urlPath.path) && Objects.equals(queryParameter, urlPath.queryParameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryParameter);
    }
}

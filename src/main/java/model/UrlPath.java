package model;

import java.util.List;
import java.util.Objects;

public class UrlPath {

    private static final List<String> FILE_EXTENSIONS = List.of(".html");

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

    public boolean hasExtension() {
        return FILE_EXTENSIONS.stream()
                .anyMatch(this.path::contains);
    }

    public String getInfo() {
        StringBuilder value = new StringBuilder();
        value.append(this.path);
        if (queryParameter != null) {
            value.append("QueryParameter :" + "\n");
            value.append(this.queryParameter.getInfo());
        }

        return value.toString();
    }

}

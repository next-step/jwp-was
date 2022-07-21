package model;

import java.util.Objects;

public class HttpPath {
    public static String DELIMITER = "\\?";
    public static int PATH_INDEX = 0;
    public static int QUERY_STRING_INDEX = 1;
    public static int LIMIT = 2;

    private String path;
    private QueryString queryString;

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }

    public HttpPath(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public HttpPath(String path) {
        this.path = path;
        this.queryString = new QueryString();
    }

    public static HttpPath Instance(String path) {
        String[] pathSplit = path.split(DELIMITER);

        return pathSplit.length >= LIMIT ? new HttpPath(pathSplit[PATH_INDEX], QueryString.parser(pathSplit[QUERY_STRING_INDEX]))
                : new HttpPath(pathSplit[PATH_INDEX]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpPath httpPath = (HttpPath) o;
        return Objects.equals(path, httpPath.path) && Objects.equals(queryString,
            httpPath.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryString);
    }
}

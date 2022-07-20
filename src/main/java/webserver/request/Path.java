package webserver.request;

import java.util.Objects;

public class Path {
    private String path;
    private QueryString queryString;

    private static final String PATH_SPLITTER = "\\?";
    private static final int LENGTH_WITH_QUERYSTRING = 2;

    private Path() {
        this.path = null;
        this.queryString = null;
    }
    private Path(String path) {
        this.path = path;
        this.queryString = null;
    }

    private Path(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Path findPath(String path) {
        String[] paths = path.split(PATH_SPLITTER);
        if(paths.length == LENGTH_WITH_QUERYSTRING) {
            return new Path(paths[0], QueryString.findQueryString(paths[1]));
        }
        return new Path(paths[0]);
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path='" + path + '\'' +
                ", queryStrings=" + queryString +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Path path1 = (Path) o;
        return Objects.equals(path, path1.path) && Objects.equals(queryString, path1.queryString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, queryString);
    }
}

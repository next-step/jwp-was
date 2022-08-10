package webserver.http.model.request;

import exception.IllegalHttpRequestException;

import java.util.Objects;

public class PathInformation {
    private static final int HAVE_QUERY_STRING = 1;

    private final Path path;
    private QueryString queryString;

    public PathInformation(String path) {
        if (!path.startsWith("/")) {
            throw new IllegalHttpRequestException("path는 /를 시작으로 하여 경로를 나타냅니다.");
        }

        String[] pathInformations = path.split("\\?");
        this.path = new Path(pathInformations[0]);
        initialQueryString(pathInformations);
    }

    private void initialQueryString(String[] pathInformations) {
        if (pathInformations.length > HAVE_QUERY_STRING) {
            this.queryString = new QueryString(pathInformations[1]);
            return;
        }
        this.queryString = null;
    }

    public String fullPath() {
        return path.resource();
    }

    public boolean isStaticResource() {
        return path.isStaticResource();
    }

    public Path getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }

    public String path() {
        return path.getPath();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathInformation pathInformation1 = (PathInformation) o;
        return Objects.equals(path, pathInformation1.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}

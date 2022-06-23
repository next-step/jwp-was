package webserver.request;


import java.util.Map;
import java.util.Objects;

public class Uri {

    private static final String URI_SPLIT_REGEX = "\\?";
    private final Path path;
    private final Query query;

    public Uri(Path path, Query query) {
        this.path = path;
        this.query = query;
    }

    public static Uri from(String uriStr) {
        String[] uriSplit = uriStr.split(URI_SPLIT_REGEX);
        Path path = Path.from(uriSplit[0]);
        if (uriSplit.length < 2) {
            return new Uri(path, Query.empty());
        }

        Query query = Query.from(uriSplit[1]);
        return new Uri(path, query);
    }

    public Path getPath() {
        return path;
    }

    public Query getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Uri)) return false;
        Uri uri = (Uri) o;
        return Objects.equals(getPath(), uri.getPath()) &&
                Objects.equals(getQuery(), uri.getQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getQuery());
    }

    public String getPathStr() {
        return path.getPath();
    }

    public boolean matchPath(String str) {
        return path.match(str);
    }

    public Map<String, String> getQueryMap() {
        return query.getQueryMap();
    }
}

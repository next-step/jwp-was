package webserver.request;


import java.util.Objects;

public class Url {

    private final Path path; // path에서 query를 제외한 경로
    private final Query query;

    public Url(Path path, Query query) {
        this.path = path;
        this.query = query;
    }

    public static Url from(String uriStr) {
        String[] uriSplit = uriStr.split("\\?");
        Path path = Path.from(uriSplit[0]);
        if (uriSplit.length < 2) {
            return new Url(path, Query.empty());
        }

        Query query = Query.from(uriSplit[1]);
        return new Url(path, query);
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
        if (!(o instanceof Url)) return false;
        Url url = (Url) o;
        return Objects.equals(getPath(), url.getPath()) &&
                Objects.equals(getQuery(), url.getQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getQuery());
    }
}

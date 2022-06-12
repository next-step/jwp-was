package webserver.request;


import java.util.Objects;

public class Path {

    private final String entry; // path에서 query를 제외한 경로
    private final Query query;

    private Path(String entry, Query query) {
        this.entry = entry;
        this.query = query;
    }

    public static Path from(String uriStr) {
        String[] uriSplit = uriStr.split("\\?");
        String path = uriSplit[0];
        Query query = Query.from(uriSplit[1]);
        return new Path(path, query);
    }

    public String getEntry() {
        return entry;
    }

    public Query getQuery() {
        return query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        Path path = (Path) o;
        return Objects.equals(getEntry(), path.getEntry()) &&
                Objects.equals(getQuery(), path.getQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntry(), getQuery());
    }
}

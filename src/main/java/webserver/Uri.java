package webserver;

public class Uri {
    private final String path;
    private final QueryString queryString;

    private Uri(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public String getPath() {
        return path;
    }

    public String getQuery(String key) {
        return queryString.get(key);
    }

    public static Uri from(String uri) {
        String[] tokens = uri.split("\\?");
        if (tokens.length <= 1) {
            return new Uri(tokens[0], new QueryString());
        }
        return new Uri(tokens[0], QueryString.from(tokens[1]));
    }
}

package webserver;

public class Uri {
    private final String path;
    private final QueryStringParser queryString;

    public Uri(String uri) {
        if (!uri.contains("?")) {
            this.path = uri;
            this.queryString = null;
        }
        else {
            this.path = uri.split("\\?")[0];
            this.queryString = new QueryStringParser(uri.split("\\?")[1]);
        }
    }

    public String getPath() {
        return path;
    }

    public QueryStringParser getQueryString() {
        return queryString;
    }


}

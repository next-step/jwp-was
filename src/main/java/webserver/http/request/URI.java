package webserver.http.request;

public class URI {
    private final String path;
    private final QueryParameters queryParameters;

    public URI(String path, QueryParameters queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public String getPath() {
        return path;
    }
}

package webserver.http.request;

public class URI {
    private final String path;

    public URI(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

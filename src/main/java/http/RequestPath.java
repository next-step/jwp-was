package http;

import java.util.Collections;

public class RequestPath {
    private final String path;

    public RequestPath(String path) {
        this.path = path;
    }

    public static RequestPath of(String path) {
        if (!path.contains("?")) {
            return new RequestPath(path);
        }
        return new RequestPath(path.split("\\?")[0]);
    }

    public String getPath() {
        return path;
    }
}

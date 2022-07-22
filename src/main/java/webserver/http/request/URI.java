package webserver.http.request;

import java.util.HashMap;

public class URI {
    private final String path;
    private final QueryParameters queryParameters;

    public URI(String path) {
        this(path, new QueryParameters(new HashMap<>()));
    }

    public URI(String path, QueryParameters queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "URI{" +
                "path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                '}';
    }
}

package webserver.http.request;

import java.util.HashMap;

public class URI {
    private final String path;
    private final Parameters queryParameters;

    public URI(String path) {
        this(path, new Parameters(new HashMap<>()));
    }

    public URI(String path, Parameters queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public String getPath() {
        return path;
    }

    public String getParameter(String key) {
        return queryParameters.get(key);
    }

    @Override
    public String toString() {
        return "URI{" +
                "path='" + path + '\'' +
                ", queryParameters=" + queryParameters +
                '}';
    }
}

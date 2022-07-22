package webserver;

import java.util.ArrayList;
import java.util.List;

public class HttpPath {
    private final List<String> pathComponents;
    private final String fullPath;
    private final HttpQueryStrings httpQueryStrings;

    public HttpPath(String rawHttpPath) {
        this.pathComponents = HttpPathComponentsBuilder.validateAndBuild(rawHttpPath);
        this.fullPath = rawHttpPath;
        this.httpQueryStrings = new HttpQueryStrings(rawHttpPath);
    }

    public String getFullPath() {
        return fullPath;
    }

    public HttpQueryStrings getHttpQueryStrings() {
        return httpQueryStrings;
    }

    public List<String> getPathComponents() {
        return new ArrayList<>(pathComponents);
    }
}

package webserver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpPath {
    private static final String PATH_SCHEMA_DELIMITER = "/";


    private final List<String> pathComponents;
    private final String fullPath;
    private final HttpQueryStrings httpQueryStrings;

    public HttpPath(String rawHttpPath) {
        List<String> pathComponents = toPathComponents(rawHttpPath);
        validateHttpPathSchemas(pathComponents);
        this.pathComponents = pathComponents;
        this.fullPath = rawHttpPath;
        this.httpQueryStrings = new HttpQueryStrings(rawHttpPath);
    }

    private List<String> toPathComponents(String rawHttpPath) {
        return Arrays.stream(rawHttpPath.split(PATH_SCHEMA_DELIMITER))
                .filter(pathComponent -> !pathComponent.isEmpty())
                .collect(Collectors.toList());
    }

    public String getFullPath() {
        return fullPath;
    }

    public HttpQueryStrings getHttpQueryStrings() {
        return httpQueryStrings;
    }

    private void validateHttpPathSchemas(List<String> pathComponents) {
        if (pathComponents.isEmpty()) {
            throw new IllegalArgumentException(String.format("잘못된 http path 입니다. HttpPath: [%s]", pathComponents));
        }
    }

    public List<String> getPathComponents() {
        return pathComponents;
    }
}

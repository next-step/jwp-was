package webserver.httprequest.requestline;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HttpPathComponentsBuilder {
    private static final String HTTP_PATH_SCHEMA_DELIMITER = "/";

    public static List<String> validateAndBuild(String fullPath) {
        List<String> httpPathComponents = toPathComponents(fullPath);

        validateHttpPathSchemas(toPathComponents(fullPath));

        return httpPathComponents;
    }

    private static void validateHttpPathSchemas(List<String> pathComponents) {
        if (pathComponents.isEmpty()) {
            throw new IllegalArgumentException(String.format("잘못된 http path 입니다. HttpPath: [%s]", pathComponents));
        }
    }

    private static List<String> toPathComponents(String fullPath) {
        return Arrays.stream(fullPath.split(HTTP_PATH_SCHEMA_DELIMITER))
                .filter(pathComponent -> !pathComponent.isEmpty())
                .collect(Collectors.toList());
    }
}

package webserver;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static webserver.HttpQueryStrings.PRIMARY_QUERY_STRING_SYMBOL;

public class HttpPathComponentsBuilder {
    private static final String HTTP_PATH_SCHEMA_DELIMITER = "/";

    public static List<String> validateAndBuild(String rawHttpPath) {
        String[] httpPathSchemas = rawHttpPath.split(PRIMARY_QUERY_STRING_SYMBOL);
        String httpFullPath = httpPathSchemas[0];

        List<String> httpPathComponents = toPathComponents(httpFullPath);

        validateHttpPathSchemas(toPathComponents(httpFullPath));

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

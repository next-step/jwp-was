package webserver.http.request.requestline;

import java.util.ArrayList;
import java.util.List;

public class HttpPath {
    public static final String PRIMARY_QUERY_STRING_SYMBOL = "\\?";

    private static final int HTTP_PATH_SCHEMAS_PATH_INDEX = 0;
    private static final int HTTP_PATH_SCHEMAS_QUERY_STRING_INDEX = 1;
    private static final int HTTP_PATH_SCHEMAS_MINIMUM_LENGTH = 1;

    private final List<String> pathComponents;
    private final String fullPath;


    private final HttpQueryStrings httpQueryStrings;

    public HttpPath(String rawHttpPath) {
        String[] httpPathSchemas = toHttpPathSchemas(rawHttpPath);
        if (httpPathSchemas.length < HTTP_PATH_SCHEMAS_MINIMUM_LENGTH) {
            throw new IllegalArgumentException("잘못된 HTTP Path 입니다.");
        }

        String fullPath = httpPathSchemas[HTTP_PATH_SCHEMAS_PATH_INDEX];

        this.pathComponents = HttpPathComponentsBuilder.validateAndBuild(fullPath);
        this.fullPath = fullPath;
        this.httpQueryStrings = toQueryStrings(httpPathSchemas);
    }

    private String[] toHttpPathSchemas(String rawHttpPath) {
        if (rawHttpPath == null || rawHttpPath.isEmpty()) {
            return new String[]{};
        }

        return rawHttpPath.split(PRIMARY_QUERY_STRING_SYMBOL);
    }

    private HttpQueryStrings toQueryStrings(String[] httpPathSchemas) {
        if (isExistsQueryString(httpPathSchemas)) {
            return new HttpQueryStrings(httpPathSchemas[HTTP_PATH_SCHEMAS_QUERY_STRING_INDEX]);
        }

        return new HttpQueryStrings("");
    }

    private boolean isExistsQueryString(String[] httpPathSchemas) {
        return httpPathSchemas.length > HTTP_PATH_SCHEMAS_MINIMUM_LENGTH;
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

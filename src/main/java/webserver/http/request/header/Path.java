package webserver.http.request.header;

import java.util.Map;

import webserver.http.request.header.exception.InvalidPathException;

public class Path {
    private static final String PARAM_DELIMITER = "\\?";
    private static final int URI_REQUEST_PARAMS_SIZE = 2;
    private final Index index;
    private final RequestParams requestParams;

    private Path(Index index, RequestParams requestParams) {
        this.index = index;
        this.requestParams = requestParams;
    }

    static Path create(String path) {
        validate(path);
        String[] pathQueryParams = path.split(PARAM_DELIMITER);
        return createPath(pathQueryParams);
    }

    String index() {
        return index.toString();
    }

    Map<String, String> requestParams() {
        return requestParams.getParams();
    }

    private static Path createPath(String[] path) {
        if (hasQueryParams(path)) {
            return new Path(new Index(path[0]), new RequestParams(path[1]));
        }
        return new Path(new Index(path[0]), new RequestParams());
    }

    private static boolean hasQueryParams(String[] path) {
        return path.length == URI_REQUEST_PARAMS_SIZE;
    }

    private static void validate(String path) {
        if (path.isEmpty()) {
            throw new InvalidPathException("유효하지 않은 path 입니다");
        }
    }
}

package webserver.header.request.requestline;

import java.util.Map;

import webserver.header.exception.InvalidPathException;

public class Path {
    public static final String PARAM_DELIMITER = "\\?";
    public static final int URI_REQUEST_PARAMS_SIZE = 2;
    private final Index index;
    private final RequestParams requestParams;

    private Path(Index index, RequestParams requestParams) {
        this.index = index;
        this.requestParams = requestParams;
    }

    public static Path create(String path) {
        validate(path);
        String[] pathQueryParams = path.split(PARAM_DELIMITER);
        return createPath(pathQueryParams);
    }

    public String index() {
        return index.toString();
    }

    public Map<String, String> requestParams() {
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

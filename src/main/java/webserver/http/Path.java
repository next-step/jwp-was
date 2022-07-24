package webserver.http;

import java.util.HashMap;

public class Path {
    private static final String QUERY_STRING_DELIMITER = "?";
    private static final int NOT_FOUND = -1;

    private final String path;

    private final Params params;

    private Path(String path) {
        this(path, Params.from(new HashMap<>()));
    }

    private Path(String path, Params params) {
        this.path = path;
        this.params = params;
    }

    public static Path of(String value) {
        int paramIndex = value.indexOf(QUERY_STRING_DELIMITER);
        if (paramIndex == NOT_FOUND) {
            return new Path(value);
        }
        final String path = value.substring(0, paramIndex);
        final Params params = Params.from(value.substring(paramIndex + 1));
        return new Path(path, params);
    }

}

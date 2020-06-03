package http;

import java.util.HashMap;
import java.util.Map;

public class Path {
    private static final String DELIMITER_OF_PATH = "\\?";

    private final String path;
    private final QueryString queryString;

    private Path(final String path,
                 final QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public static Path of(final String pathValue) {
        final String[] values = pathValue.split(DELIMITER_OF_PATH);
        return new Path(values[0], QueryString.of(values[1]));
    }
}

package webserver.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpPath {
    private static final String REGEX = "^/\\w*";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private final String path;

    public HttpPath(String path) {
        validate(path);
        this.path = path;
    }

    private void validate(String path) {
        Matcher matcher = PATTERN.matcher(path);
        if (!matcher.find()) {
            throw new IllegalArgumentException("invalid path: " + path);
        }
    }
}

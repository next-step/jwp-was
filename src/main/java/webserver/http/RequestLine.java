package webserver.http;

import utils.HttpStringType;
import utils.HttpStringUtils;

public class RequestLine {
    private String method;
    private Path path;

    private RequestLine(String method, Path path) {
        this.method = method;
        this.path = path;
    }
    public static RequestLine parse(String requestLine) {
        validate(requestLine);

        String[] requestLineTokens = HttpStringUtils.split(requestLine, HttpStringType.REQUESTLINE_DELIMITER.getType());
        return new RequestLine(requestLineTokens[0], Path.newInstance(requestLineTokens[1]));
    }

    private static void validate(String requestLine) {
        if (requestLine == null || requestLine.length() == 0) {
            throw new IllegalArgumentException();
        }
    }

    public String getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }
}
package model;

import java.io.File;

/**
 * @author : yusik
 * @date : 2019-08-01
 */
public class RequestLine {

    private static final String LINE_SEPARATOR = " ";
    private String method;
    private String path;

    public RequestLine(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public static RequestLine parse(String requestLine) {
        String[] parsedLines = requestLine.split(LINE_SEPARATOR);
        return new RequestLine(parsedLines[0], parsedLines[1]);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}

package webserver.http;

import utils.HttpStringType;
import utils.HttpStringUtils;

import java.util.regex.Pattern;

public class Path {
    private String path;
    private Parameters parameters;

    private Path(String path, Parameters parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static Path newInstance(String pathString) {
        if (isQueryStringExist(pathString)) {
            return new Path(parsePath(pathString), Parameters.newInstance(parseQueryString(pathString)));
        }

        return new Path(pathString, Parameters.emptyInstance());
    }

    private static boolean isQueryStringExist(String path) {
        return Pattern.matches(HttpStringType.PATH_PATTERN.getType(), path);
    }

    private static String parsePath(String pathString) {
        return HttpStringUtils.splitAndFindByIndex(pathString, HttpStringType.DELIMITER_QUESTION_MARK.getType(), 0);
    }

    private static String parseQueryString(String pathString) {
        return HttpStringUtils.splitAndFindByIndex(pathString, HttpStringType.DELIMITER_QUESTION_MARK.getType(), 1);
    }

    public String getPath() {
        return path;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void addParameters(String requestBody) {
        parameters.addAll(requestBody);
    }
}

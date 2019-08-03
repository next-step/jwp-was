package webserver.http;

import utils.HttpStringType;
import utils.HttpStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Path {
    private String path;
    private List<Parameter> parameters;

    private Path(String path, List<Parameter> parameters) {
        this.path = path;
        this.parameters = parameters;
    }

    public static Path newInstance(String pathString) {
        if (isQueryStringExist(pathString)) {
            return new Path(parsePath(pathString), parseQueryString(pathString));
        }

        return new Path(pathString, new ArrayList<>());
    }

    private static boolean isQueryStringExist(String path) {
        return Pattern.matches(HttpStringType.QUERYSTRING_PATTERN.getType(), path);
    }

    private static String parsePath(String requestLineToken) {
        return splitByIndex(requestLineToken, HttpStringType.QUERYSTRING_DELIMITER.getType(), 0);
    }

    private static List<Parameter> parseQueryString(String requestLineToken) {
        List<Parameter> parameters = new ArrayList<>();

        String queryString = splitByIndex(requestLineToken,HttpStringType.QUERYSTRING_DELIMITER.getType(), 1);
        String[] queryStringTokens = split(queryString, HttpStringType.PARAMETER_DELIMITER.getType());

        for (String parameter : queryStringTokens) {
            String[] data = split(parameter, HttpStringType.VALUE_DELIMITER.getType());
            parameters.add(Parameter.newInstance(data[0], data[1]));
        }

        return parameters;
    }

    private static String[] split(String value, String delimiter) {
        return value.split(delimiter);
    }

    private static String splitByIndex(String value, String delimiter, int index) {
        String[] valueTokens = value.split(delimiter);
        return valueTokens[index];
    }

    public String getPath() {
        return path;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }
}

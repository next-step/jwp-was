package utils;

public class QueryStringParser implements Parser {
    private static final String QUERYSTRING_SEPARATOR = "?";

    public String parse(String path) {

        return path.substring(path.indexOf(QUERYSTRING_SEPARATOR) + 1);
    }
}
package utils.parser;

public class QueryStringParser {
    private static final String QUERYSTRING_SEPARATOR = "?";

    public static String parse(String path) {

        return path.substring(path.indexOf(QUERYSTRING_SEPARATOR) + 1);
    }

    public static String removeQueryString(String path) {
        String queryString = path.substring(path.indexOf(QUERYSTRING_SEPARATOR));

        return path.replace(queryString, "");
    }
}
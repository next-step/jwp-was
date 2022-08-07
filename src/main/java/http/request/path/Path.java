package http.request.path;

public class Path {

    private static final String QUERY_PARAMETER_DELIMITER = "\\?";
    private static final int PATH_ONLY_INDEX = 0;
    private static final int QUERYSTRING_INDEX = 1;
    private static final int QUERY_STRING_EMPTY = 1;

    private String path;
    private QueryParameters queryParameters;

    private Path(String path) {
        this.path = path;
    }
    private Path(String path, QueryParameters queryParameters) {
        this.path = path;
        this.queryParameters = queryParameters;
    }

    public static Path from(String pathWithParam) {
        String[] pathWithParams = pathWithParam.split(QUERY_PARAMETER_DELIMITER);
        if (queryStringExist(pathWithParams)) {
            return new Path(pathWithParams[PATH_ONLY_INDEX], QueryParameters.from(pathWithParams[QUERYSTRING_INDEX]));
        }
        return new Path(pathWithParams[PATH_ONLY_INDEX]);
    }

    private static boolean queryStringExist(String[] pathWithParams) {
        return pathWithParams.length != QUERY_STRING_EMPTY;
    }

    public String getPathWithOutParam() {
        return path;
    }

    public String getQueryParameters(String key) {
        return queryParameters.get(key);
    }
}

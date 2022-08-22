package model;

import webserver.RequestParameters;

public class HttpPath {
    private static final String QUERY_STRING_DELIMITER = "\\?";
    private static final int PATH_POSITION = 0;
    private static final int STRING_QUERY_POSITION = 1;

    private String path;
    private RequestParameters requestParameters;

    public HttpPath(String path) {
        RequestParameters requestParameters = new RequestParameters();
        if (path.contains("?")) {
            String[] parsingPath = path.split(QUERY_STRING_DELIMITER);
            path = parsingPath[PATH_POSITION];
            String stringQuery = parsingPath[STRING_QUERY_POSITION];
            requestParameters = parsingQueryString(stringQuery);
        }

        this.path = path;
        this.requestParameters = requestParameters;
    }

    public String getPath() {
        return path;
    }

    public RequestParameters getRequestParameters() {
        return requestParameters;
    }

    private static RequestParameters parsingQueryString(String path) {
        return new RequestParameters(path);
    }
}

package webserver.http.request;

/**
 * @author : yusik
 * @date : 2019-08-03
 */
public class RequestURI {

    private static final String START_OF_QUERY = "?";
    private String path;
    private QueryString queryString;

    public static RequestURI parse(String requestURI) {
        String path = requestURI;
        QueryString queryString = null;
        int queryIndex = requestURI.indexOf(START_OF_QUERY);
        if (queryIndex >= 0) {
            path = requestURI.substring(0, queryIndex);
            queryString = QueryString.parse(requestURI.substring(queryIndex + 1));
        }
        return new RequestURI(path, queryString);
    }

    private RequestURI(String path, QueryString queryString) {
        this.path = path;
        this.queryString = queryString;
    }

    public String getPath() {
        return path;
    }

    public QueryString getQueryString() {
        return queryString;
    }
}

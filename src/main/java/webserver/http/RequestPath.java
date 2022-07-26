package webserver.http;

public class RequestPath {

    private String path;
    private String queryString;

    public RequestPath(String requestPath) {
        String[] splitPath = requestPath.split("\\?");
        String queryString = "";

        this.path = splitPath[0];
        if (splitPath.length == 2) {
            queryString = splitPath[1];
        }

        this.queryString = queryString;
    }

    public String getPath() {
        return path;
    }

    public String getQueryString() {
        return queryString;
    }
}

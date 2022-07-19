package webserver;

public class RequestPath {

    private static final String QUERY_PARAM_SEPERATOR = "?";

    private String path;

    public RequestPath(String path) {
        int queryParamIndex = path.indexOf(QUERY_PARAM_SEPERATOR);
        String tempPath = path;
        if (queryParamIndex > -1) {
            tempPath = path.substring(0, queryParamIndex);
        }
        this.path = tempPath;
    }

    public String getPath() {
        return path;
    }
}
